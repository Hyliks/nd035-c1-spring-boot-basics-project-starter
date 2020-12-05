package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeModelService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home/notes")
public class NoteController {

    private NoteService noteService;
    private HomeModelService homeModelService;
    private UserService userService;

    public NoteController(NoteService noteService, HomeModelService homeModelService, UserService userService) {
        this.noteService = noteService;
        this.homeModelService = homeModelService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addOrEditNote(String noteId, String noteTitle, String noteDescription, Model model, Authentication authentication) {
        User activeUser = this.userService.getActiveUser(authentication);

        if(noteId != "") {
            this.noteService.editNote(new Note(Integer.parseInt(noteId),noteTitle,noteDescription,activeUser.getUserId()));
        } else {
            this.noteService.addNote(new Note(noteTitle,noteDescription),activeUser);
        }

        this.homeModelService.resetUnEncryptedCredential();
        this.homeModelService.setOpenTab("note");
        return "redirect:/home";
    }

    @PostMapping("/delete")
    public String deleteNote(String noteId, Model model, Authentication authentication) {
        User activeUser = this.userService.getActiveUser(authentication);

        this.noteService.deleteNote(new Note(Integer.parseInt(noteId)));

        this.homeModelService.resetUnEncryptedCredential();
        this.homeModelService.setOpenTab("note");
        return "redirect:/home";
    }
}
