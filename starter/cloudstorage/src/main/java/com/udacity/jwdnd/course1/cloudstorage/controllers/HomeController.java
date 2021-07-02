package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.boot.Banner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;

@Controller
public class HomeController {

    private final FileService fileService;
    private final FilesMapper filesMapper;
    private final NotesService notesService;
    private final CredentialsService credentialsService;
    private final EncryptionService encryptionService;
    private final UserService userService;

    public HomeController(FileService fileService, FilesMapper filesMapper, NotesService notesService,
                          CredentialsService credentialsService, EncryptionService encryptionService, UserService userService) {
        this.fileService = fileService;
        this.filesMapper = filesMapper;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHomepage(Model model, Authentication authentication){


        User user = userService.getUserByName(authentication.getName());
        model.addAttribute("files", this.fileService.getUsersFiles(user.getUserid()));
        model.addAttribute("notes", this.notesService.getAllNotes(authentication));
        model.addAttribute("credentials", this.credentialsService.getAllCredentials(authentication));
        model.addAttribute("encryptionService", this.encryptionService);
        model.addAttribute("fileSuccess", false);
        return "home";
    }

/* Methods for Uploading, Downloading and Deleting files */

   @GetMapping("/deletefile")
    public String deleteFile(@RequestParam Integer fileId, Authentication authentication, Model model)  {
        filesMapper.delete(fileId);
        model.addAttribute("files", this.fileService.getUsersFiles(Integer.valueOf(authentication.getName())));
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
    public String uploadFile(@RequestParam("fileUpload")MultipartFile multipartFile, Model model, Authentication authentication, RedirectAttributes redirectAttributes) throws IOException {

       User user = userService.getUserByName(authentication.getName());

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        String uploadError = null;

        if (fileName.equals("")){
            uploadError = "Please choose the file";
        }

        if (!fileService.isFilesNameAvailable(fileName, user.getUserid())) {
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

        model.addAttribute("files", this.fileService.getUsersFiles(Integer.valueOf(authentication.getName())));

        return "home";

    }

    /*Methods for adding, editing and deleting notes*/

    @PostMapping("/notes")
    public String postNote(@ModelAttribute("newNote") Note newNote, @RequestParam("noteid") Integer noteid, Model model, Authentication authentication, RedirectAttributes redirectAttributes){

        User user = userService.getUserByName(authentication.getName());

        newNote.setUserid(user.getUserid());
        int row = notesService.createOrUpdateNote(newNote);


        model.addAttribute("notes", this.notesService.getAllNotes(authentication));

        boolean changeSuccess = true;

        if (row > 0) {

            redirectAttributes.addAttribute("changeSuccess", changeSuccess);
        } else {

            redirectAttributes.addAttribute("changeSuccess", !changeSuccess);
            redirectAttributes.addAttribute("errorMessage", "There was a problem while adding a note");
        }

       return "redirect:/result";
    }

    @GetMapping("/notes")
    public String deleteNote(@RequestParam("noteId") Integer noteId, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {


        int row =  notesService.deleteFile(noteId);
         model.addAttribute("notes", this.notesService.getAllNotes(authentication));
        boolean changeSuccess = true;

        if (row > 0) {

            redirectAttributes.addAttribute("changeSuccess", changeSuccess);
        } else {

            redirectAttributes.addAttribute("changeSuccess", !changeSuccess);
            redirectAttributes.addAttribute("errorMessage", "There was a problem while deleting a note");
        }
        return "redirect:/result";

    }

    @PostMapping("/credentials")
    public String postCredential(@ModelAttribute("newCredential")Credential credential, @RequestParam("credentialid") Integer credentialid, Model model, Authentication authentication, RedirectAttributes redirectAttribute){

            int row = 0;

            if (credential.getCredentialid() == null) {
                row = credentialsService.createCredential(credential.getUrl(), credential.getUsername(), credential.getPassword(), Integer.valueOf(authentication.getName()));
                model.addAttribute("credentials", this.credentialsService.getAllCredentials(authentication));
                model.addAttribute("encryptionService", this.encryptionService);
            } else {
                 row = credentialsService.updateCredential(credential, credentialid, authentication);
                model.addAttribute("credentials", this.credentialsService.getAllCredentials(authentication));
                model.addAttribute("encryptionService", this.encryptionService);
            }

            boolean changeSuccess = true;

            if (row > 0) {

                redirectAttribute.addAttribute("changeSuccess", changeSuccess);
            } else {

                redirectAttribute.addAttribute("changeSuccess", !changeSuccess);
                redirectAttribute.addAttribute("errorMessage", "There was a problem while updating a note");
            }


    return "redirect:/result";
    }

    @GetMapping("/credentials")
    public String deleteCredential(@RequestParam("credentialId") Integer credentialid, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {

        int row = credentialsService.deleteCredential(credentialid);
        model.addAttribute("credentials", this.credentialsService.getAllCredentials(authentication));
        model.addAttribute("encryptionService", this.encryptionService);
        boolean changeSuccess = true;

        if (row > 0) {

            redirectAttributes.addAttribute("changeSuccess", changeSuccess);
        } else {

            redirectAttributes.addAttribute("changeSuccess", !changeSuccess);
            redirectAttributes.addAttribute("errorMessage", "There was a problem while updating a note");
        }

        return "redirect:/result";
    }



}
