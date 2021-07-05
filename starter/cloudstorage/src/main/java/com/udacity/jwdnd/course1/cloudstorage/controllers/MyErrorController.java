package com.udacity.jwdnd.course1.cloudstorage.controllers;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {


    @RequestMapping("/error")
    public String handleErrors(){

        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
