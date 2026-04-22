package com.example.Assignment1.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Assignment1.model.UserEntity;
import com.example.Assignment1.service.UserService;

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody  UserEntity userEntity){
      try {
          userService.createUser(userEntity);
          return ResponseEntity.status(HttpStatus.CREATED).body(userEntity);
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while creating User: " + e.getMessage());
      }  
    } 
}
