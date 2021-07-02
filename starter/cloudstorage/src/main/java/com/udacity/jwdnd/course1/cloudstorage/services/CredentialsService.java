package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.awt.desktop.UserSessionEvent;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;
    private final UserService userService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService, UserService userService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    public List<Credential> getAllCredentials(Authentication authentication){
        User user = userService.getUserByName(authentication.getName());
        return credentialsMapper.getCredentialsByUser(user.getUserid());
    }

    public Credential getCredential(Integer credentialid, Integer userid){
        return credentialsMapper.getCredential(credentialid, userid);
    }

    public int createCredential(String url, String username, String password, Integer userid){

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        return credentialsMapper.insert(new Credential(null, url, username, encodedKey, encryptedPassword, userid ));

    }

    public int deleteCredential(Integer credentialid){
        return credentialsMapper.delete(credentialid);
    }

    public int updateCredential(Credential credential, Integer credentialid, Authentication authentication){

        Credential tmp = credentialsMapper.getCredential(credentialid, Integer.valueOf(authentication.getName()));
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), tmp.getKey());
        tmp.setPassword(encryptedPassword);
        tmp.setUrl(credential.getUrl());
        tmp.setUsername(credential.getUsername());
        return credentialsMapper.update(tmp);
    }
}
