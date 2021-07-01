package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
public class HomeController {

    private final FileService fileService;
    private final FilesMapper filesMapper;

    public HomeController(FileService fileService, FilesMapper filesMapper) {
        this.fileService = fileService;
        this.filesMapper = filesMapper;
    }

    @GetMapping("/home")
    public String getHomepage(Model model, Authentication authentication){

        model.addAttribute("files", this.fileService.getUsersFiles(authentication.getName()));
        model.addAttribute("fileSuccess", false);
        return "home";
    }

/* Methods for Uploading, Downloading and Deleting files */

   @GetMapping("/deletefile")
    public String deleteFile(@RequestParam Integer fileId, Authentication authentication, Model model)  {
        filesMapper.delete(fileId);
        model.addAttribute("files", this.fileService.getUsersFiles(authentication.getName()));
        return "redirect:/home";
    }

    @GetMapping("/fileview")
    public ResponseEntity handleFileView(@RequestParam Integer fileId) {
        File file = fileService.getFile(fileId);
        System.out.println(file.getContenttype());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .body(file.getFiledata());
    }


    @PostMapping("/home")
    public String uploadFile(@RequestParam("fileUpload")MultipartFile multipartFile, Model model, Authentication authentication) throws IOException {


        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        String uploadError = null;

        if (fileName.equals("")){
            uploadError = "Please choose the file";
        }

        if (!fileService.isFilesNameAvailable(fileName, authentication.getName())) {
            uploadError = "There is a file with the same name";
        }

        if (uploadError == null) {
            int rowsAdded = fileService.createFile(multipartFile, authentication);
            if (rowsAdded < 0) {
                uploadError = "There was an error uploading your file. Please try again.";
            }
        }

        if (uploadError != null) {

            model.addAttribute("fileSuccess", true);
            model.addAttribute("fileError", uploadError);
        } else {

            model.addAttribute("fileSuccess", false);
        }

        model.addAttribute("files", this.fileService.getUsersFiles(authentication.getName()));

        return "home";

    }

}
