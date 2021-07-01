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
    private final UserMapper userMapper;

    public FileService (FilesMapper filesMapper, UserMapper userMapper){
        this.filesMapper = filesMapper;
        this.userMapper = userMapper;
    }

    public List<File> getFiles(){
        return this.filesMapper.getAllFiles();
    }

    public boolean isFilesNameAvailable(String filename){
        return filesMapper.getFile(filename) == null;
    }

    public int createFile(MultipartFile file, User user, Authentication authentication) throws IOException {

        System.out.println("File created");

        return filesMapper.insert(new File(null, file.getOriginalFilename(), file.getContentType(), Long.toString(file.getSize()), user.getUserid(), file.getBytes()));


    }





}
