package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final FilesMapper filesMapper;
    private final UserService userService;

    public FileService (FilesMapper filesMapper, UserService userService){
        this.filesMapper = filesMapper;
        this.userService = userService;
    }

    public List<File> getFiles(){
        return this.filesMapper.getAllFiles();
    }

    public File getFile(Integer fileId){ return filesMapper.getFile(fileId);};

    public byte[] getFilesData(Integer fileId){
        return filesMapper.getFile(fileId).getFiledata();
    }

    public List<File> getUsersFiles(Integer userid) { return this.filesMapper.getUsersFiles(userid);}

    public boolean isFilesNameAvailable(String filename, Integer userid){
        return filesMapper.getFileByNameAndUser(filename, userid) == null;
    }

    public int createFile(MultipartFile file, Authentication authentication) throws IOException {

        System.out.println("File created");
        User user = userService.getUserByName(authentication.getName());

        return filesMapper.insert(new File(null, file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), user.getUserid(), file.getBytes()));


    }






}
