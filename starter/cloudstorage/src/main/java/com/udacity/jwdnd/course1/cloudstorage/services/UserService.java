package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private UserMapper mapper;
    private HashService hash;

    public UserService(UserMapper mapper, HashService hash) {
        this.mapper = mapper;
        this.hash = hash;
    }

    public int create(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hash.getHashedValue(user.getPassword(), encodedSalt);

        return mapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }

    public User get(String username) {
        return this.mapper.getUserByUsername(username);
    }

    public boolean usernameValid(String username) {
        return this.get(username) == null;
    }

    public User getActiveUser(Authentication authentication) {
        return this.get((authentication.getName()));
    }
}
