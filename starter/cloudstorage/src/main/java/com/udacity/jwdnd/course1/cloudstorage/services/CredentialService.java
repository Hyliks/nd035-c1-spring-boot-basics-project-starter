package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper mapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper mapper, EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
        this.mapper = mapper;
    }

    public Credential addCredential(Credential credential, User user) {
        credential.setUserId(user.getUserId());

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        credential.setKey(Base64.getEncoder().encodeToString(key));

        credential.setPassword(encryptionService.encryptValue(credential.getPassword(),credential.getKey()));

        credential.setCredentialId(this.mapper.insert(credential));
        return credential;
    }

    public void editCredential(Credential credential) {
        String password = credential.getPassword();
        credential = this.mapper.getCredential(credential.getCredentialId(),credential.getUserId());
        credential.setPassword(password);

        credential.setPassword(encryptionService.encryptValue(credential.getPassword(),credential.getKey()));
        this.mapper.update(credential);
    }

    public void deleteCredential(Credential credential) {
        this.mapper.delete(credential);
    }

    public List<Credential> getCredentials(Integer userId) {
        return this.mapper.getCredentials(userId);
    }

    public Credential getDecodedCredential(Integer credentialId, Integer userId) {
        Credential credential = this.mapper.getCredential(credentialId, userId);
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(),credential.getKey()));
        return credential;
    }
}
