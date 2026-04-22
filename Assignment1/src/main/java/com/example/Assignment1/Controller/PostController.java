package com.example.Assignment1.Controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Assignment1.Repository.PostRepository;
import com.example.Assignment1.model.CommentEntity;
import com.example.Assignment1.model.PostEntity;
import com.example.Assignment1.service.CommentService;
import com.example.Assignment1.service.PostService;
import com.example.Assignment1.service.ViralityService;

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    private final PostRepository PostRepository;
    private final CommentService commentService;
    private final ViralityService viralityService;
    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody PostEntity postEntity){
        try {
            postService.createPost(postEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }
    @PostMapping("/post/like")
    public ResponseEntity<String> likePost(@RequestHeader UUID postId){
       try {
        PostEntity post=PostRepository.findById(postId).orElseThrow(()->new Exception("Post not found"));
        postService.updateLikes(post.getPostTitle());
        return ResponseEntity.ok("Post liked successfully");
       } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
       }
    }
    @GetMapping("/posts/getlikes")
    public ResponseEntity<String> getLikes(@RequestParam String postTitle){
       try {
        Integer likes=postService.getLikes(postTitle);
        return ResponseEntity.ok("Post has "+likes+" likes");
       } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
       }
    }
    @PostMapping("/posts/comments")
    public ResponseEntity<String> createComment(@RequestHeader UUID postId,@RequestBody CommentEntity comment){
        try {
            commentService.CreateComment(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body("Comment created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating comment");
        }
    }
    @GetMapping("/posts/getVirality")
    public ResponseEntity<String> getVirality(@RequestParam String postTitle){
        try {
            Integer viralityScore=viralityService.getViralityScore(postTitle);
            return ResponseEntity.ok("Post has a virality score of "+viralityScore);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
    }

}
