package com.example.Assignment1.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Assignment1.model.PostEntity;
import com.example.Assignment1.service.PostService;

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody PostEntity postEntity){
        try {
            postService.createPost(postEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

}
