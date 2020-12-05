package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeModelService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private HomeModelService homeModelService;
    private UserService userService;


    public HomeController(HomeModelService homeModelService,UserService userService) {
        this.userService = userService;
        this.homeModelService = homeModelService;
    }

    @GetMapping()
    public String homeView(Model model, Authentication authentication) {
        User user = this.userService.getActiveUser(authentication);
        model = this.homeModelService.updateModel(model, user);
        return "home";
    }

}
