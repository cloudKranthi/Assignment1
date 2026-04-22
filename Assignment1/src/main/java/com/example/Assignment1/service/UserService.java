package com.example.Assignment1.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Assignment1.Repository.UserRepository;
import com.example.Assignment1.model.UserEntity;
@Service
public class UserService {
    @Autowired
    private UserRepository  userRepository;
    public void createUser(UserEntity user) {
        userRepository.save(user);
    }
}
