package com.example.Assignment1.service;
import com.example.Assignment1.Repository.PostRepository;
import com.example.Assignment1.model.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    public void createPost(PostEntity post) {
        postRepository.save(post);
    }
}
