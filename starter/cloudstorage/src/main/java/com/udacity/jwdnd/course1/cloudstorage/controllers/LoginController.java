package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginView(Model model, @RequestParam(defaultValue = "false", name="signupSuccess") boolean signupSuccess){

        model.addAttribute("signupSuccess", signupSuccess);

        return "login";
    }

   }
