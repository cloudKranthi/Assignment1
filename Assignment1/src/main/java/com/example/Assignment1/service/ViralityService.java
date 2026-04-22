package com.example.Assignment1.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.Assignment1.Repository.PostRepository;
import com.example.Assignment1.model.PostEntity;
@Service
public class ViralityService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private PostRepository postRepository;
    public Integer getViralityScore(String postTitle){
    
      PostEntity post=postRepository.findByPostTitle(postTitle);
      String cacheKey="post"+post.getId().toString()+":virality_score";
      
        String viralityScore=redisTemplate.opsForValue().get(cacheKey);
        if(viralityScore==null){
            viralityScore="0";
        }
        return Integer.parseInt(viralityScore);
      
    }
}
