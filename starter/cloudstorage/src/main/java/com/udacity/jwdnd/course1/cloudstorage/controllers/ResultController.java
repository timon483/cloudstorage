package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResultController {

    @GetMapping("/result")
    public String getHomepage(Model model, @RequestParam("changeSuccess") boolean changeSuccess, @RequestParam( defaultValue = "", name = "errorMessage") String errorMessage) {

       // boolean changeSuccess = true;

        model.addAttribute("changeSuccess", changeSuccess);
        model.addAttribute("changeError", !changeSuccess);
        model.addAttribute("changeErrorMessage", errorMessage);
        return "result";
    }
}
