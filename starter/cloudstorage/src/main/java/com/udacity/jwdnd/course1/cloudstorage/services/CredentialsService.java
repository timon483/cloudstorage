package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentials(Authentication authentication){
        return credentialsMapper.getCredentialsByUser(Integer.valueOf(authentication.getName()));
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

    public int updateCredential(Credential credential){
        return credentialsMapper.update(credential);
    }
}
