package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.codec.ByteArrayDecoder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Document;
import java.io.*;
import java.net.FileNameMap;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class HomeController {

    private final FileService fileService;
    private final UserService userService;
    private final FilesMapper filesMapper;

    public HomeController(FileService fileService, UserService userService, FilesMapper filesMapper) {
        this.fileService = fileService;
        this.userService = userService;
        this.filesMapper = filesMapper;
    }

    @GetMapping("/home")
    public String getHomepage(Model model){

        model.addAttribute("files", this.fileService.getFiles());
        model.addAttribute("signupSuccess", false);
        return "home";
    }

    @RequestMapping(value = "/home/{filename}", method = RequestMethod.GET)
    public void getFile(
            @PathVariable("filename") String filename,
            HttpServletResponse response) {

        File file = filesMapper.getFile(filename);
        java.io.File file1 = new java.io.File(filename);


        try {
            // get your file as InputStream
            InputStream is = new ByteArrayInputStream(file.getFiledata());
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.setContentType(file.getContenttype());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }

    }

    /*@GetMapping("/home/{filename}")
    @ResponseBody
    public ResponseEntity<java.io.File> serveFile(@PathVariable String filename) throws IOException {



        System.out.println("Something is happening");
        File file = filesMapper.getFile(filename);
        java.io.File file1 = new java.io.File(filename);

        FileOutputStream fos = new FileOutputStream(file1);
        fos.write(file.getFiledata());
        fos.close();

        Path path = Paths.get("./"+filename );

        return  ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file1);
    }*/

    @PostMapping("/home/{filename}")
    @ResponseBody
    public String deleteFile(@PathVariable String filename, Model model) throws IOException {

        filesMapper.delete(filename);
        model.addAttribute("files", this.fileService.getFiles());
        return "home";
    }

    @PostMapping("/home")
    public String uploadFile(@RequestParam("fileUpload")MultipartFile multipartFile, Model model, User user, Authentication authentication) throws IOException {


        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        String uploadError = null;

        if (!fileService.isFilesNameAvailable(fileName)) {
            uploadError = "There is a file with the same name";
            System.out.println(uploadError);
        }



        if (uploadError == null) {
            int rowsAdded = fileService.createFile(multipartFile, user, authentication);
            if (rowsAdded < 0) {
                uploadError = "There was an error uploading your file. Please try again.";
                System.out.println(uploadError);
            }
        }

        if (uploadError != null) {

            model.addAttribute("signupSuccess", true);
            model.addAttribute("signupError", uploadError);
        } else {

            model.addAttribute("signupSuccess", false);
        }

        model.addAttribute("files", this.fileService.getFiles());

        return "home";



    }

}
