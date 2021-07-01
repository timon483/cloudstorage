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

    public FileService (FilesMapper filesMapper){
        this.filesMapper = filesMapper;
    }

    public List<File> getFiles(){
        return this.filesMapper.getAllFiles();
    }

    public File getFile(Integer fileId){ return filesMapper.getFile(fileId);};

    public byte[] getFilesData(Integer fileId){
        return filesMapper.getFile(fileId).getFiledata();
    }

    public List<File> getUsersFiles(String username) { return this.filesMapper.getUsersFiles(username);}

    public boolean isFilesNameAvailable(String filename, String username){
        return filesMapper.getFileByNameAndUser(filename, username) == null;
    }

    public int createFile(MultipartFile file, Authentication authentication) throws IOException {

        System.out.println("File created");

        return filesMapper.insert(new File(null, file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), authentication.getName(), file.getBytes()));


    }






}
