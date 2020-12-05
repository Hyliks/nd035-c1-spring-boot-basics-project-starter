package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeModelService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home/credentials")
public class CredentialController {

    private CredentialService credentialService;
    private HomeModelService homeModelService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, HomeModelService homeModelService, UserService userService) {
        this.credentialService = credentialService;
        this.homeModelService = homeModelService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addOrEditNote(String credentialId, String url, String username, String password, Model model, Authentication authentication) {
        User activeUser = this.userService.getActiveUser(authentication);

        if(credentialId != "-1" && credentialId != "") {
            this.credentialService.editCredential(new Credential(Integer.parseInt(credentialId),url,username,password,activeUser.getUserId()));
        } else {
            this.credentialService.addCredential(new Credential(url,username,password),activeUser);
        }

        this.homeModelService.setOpenTab("credential");
        this.homeModelService.resetUnEncryptedCredential();
        return "redirect:/home";
    }

    @PostMapping("/unencoded")
    public String getUnencoded(String credentialId, Model model, Authentication authentication) {
        User activeUser = this.userService.getActiveUser(authentication);

        Credential credential = this.credentialService.getDecodedCredential(Integer.parseInt(credentialId),activeUser.getUserId());

        this.homeModelService.setOpenTab("credential");
        this.homeModelService.setUnEncryptedCredential(credential);
        return "redirect:/home";
    }

    @PostMapping("/delete")
    public String deleteNote(String credentialId, Model model, Authentication authentication) {
        User activeUser = this.userService.getActiveUser(authentication);

        this.credentialService.deleteCredential(new Credential(Integer.parseInt(credentialId)));

        this.homeModelService.setOpenTab("credential");
        this.homeModelService.resetUnEncryptedCredential();
        return "redirect:/home";
    }
}
