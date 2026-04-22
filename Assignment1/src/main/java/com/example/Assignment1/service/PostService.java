package com.example.Assignment1.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.Assignment1.Repository.PostRepository;
import com.example.Assignment1.model.PostEntity;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class PostService {
    private final StringRedisTemplate redisTemplate;
    @Autowired
    private PostRepository postRepository;
    public void createPost(PostEntity post) {
        postRepository.save(post);
    }
    @Transactional
    public void updateLikes(String postTitle) {
        PostEntity post = postRepository.findByPostTitle(postTitle);
        String cacheKey="post:"+post.getId().toString()+":likes";
        redisTemplate.opsForValue().increment(cacheKey,1);
        String viralityKey="post:"+post.getId().toString()+":virality_score";
        redisTemplate.opsForValue().increment(viralityKey,50);
    }
    @Transactional
    public Integer getLikes(String PostTitle){
        PostEntity post=postRepository.findByPostTitle(PostTitle);
         String cacheKey="post:"+post.getId().toString()+":likes";
         String likes=redisTemplate.opsForValue().get(cacheKey);
         return Integer.parseInt(likes);
    }
}
