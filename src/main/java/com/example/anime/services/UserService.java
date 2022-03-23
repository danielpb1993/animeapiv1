package com.example.anime.services;

import com.example.anime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean ifExists(String username){
        return userRepository.findByUsername(username) != null;
    }

    public UUID getUserId(String username){
        return userRepository.findByUsername(username).userid;
    }
}
