package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeModelService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.catalina.webresources.FileResource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/home/file")
public class FileController {
    private FileService fileService;
    private HomeModelService homeModelService;
    private UserService userService;

    public FileController(FileService fileService, HomeModelService homeModelService, UserService userService) {
        this.fileService = fileService;
        this.homeModelService = homeModelService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model, Authentication authentication) {
        User activeUser = this.userService.getActiveUser(authentication);
        try {
            File file = this.fileService.getFileByName(fileUpload.getOriginalFilename());

            InputStream ins = fileUpload.getInputStream();

            if(file == null) {
                this.fileService.addFile(new File(fileUpload.getOriginalFilename(), fileUpload.getContentType(), String.valueOf(fileUpload.getSize()), ins), activeUser);
                model.addAttribute("filesuccess",true);
            } else {
                model.addAttribute("fileerror","File already exists. Please rename it.");
                model.addAttribute("filesuccess",false);
            }
        }catch (IOException e) {
            model.addAttribute("fileerror","File upload failed. Please try again.");
            model.addAttribute("filesuccess",false);
        }

        this.homeModelService.resetUnEncryptedCredential();
        return "result";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, Model model, Authentication authentication) throws IOException{
        User activeUser = this.userService.getActiveUser(authentication);

        File file = this.fileService.getFile(Integer.parseInt(fileId),activeUser.getUserId());


        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(new ByteArrayResource(file.getFiledata().readAllBytes()));
    }

    @PostMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable String fileId, Model model, Authentication authentication) {
        User activeUser = this.userService.getActiveUser(authentication);

        this.homeModelService.resetUnEncryptedCredential();
        return "redirect:/home";
    }
}
