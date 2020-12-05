package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class HomeModelService {
    private NoteService noteService;
    private CredentialService credentialService;
    private FileService fileService;

    private String openTab;
    private Credential credential;


    public HomeModelService(NoteService noteService, CredentialService credentialService, FileService fileService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.fileService = fileService;

        this.openTab = "file";
        this.credential = new Credential(-1,"","","","");
    }

    public void setOpenTab(String openTab) {
        this.openTab = openTab;
    }

    public void setUnEncryptedCredential(Credential credential) {
        this.credential = credential;
    }

    public void resetUnEncryptedCredential() {
        this.credential = new Credential(-1,"","","","");
    }

    public Model updateModel(Model model, User user) {
        model.addAttribute("openTab", openTab);
        model.addAttribute("notes",noteService.getNotes(user.getUserId()));
        model.addAttribute("credentials",credentialService.getCredentials(user.getUserId()));
        model.addAttribute("files",fileService.getFiles(user.getUserId()));
        model.addAttribute("credential",credential);
        return model;
    }
}
