package com.example.Assignment1.service;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import com.example.Assignment1.model.PostEntity;

import com.example.Assignment1.Repository.PostRepository;
@Service
public class ViralityService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private PostRepository postRepository;
    public Integer getViralityScore(UUID authour_id){
    
      PostEntity post=postRepository.getAuthor_id(authour_id);
      String cacheKey="post"+post.getId().toString()+":virality_score";
      
        String viralityScore=redisTemplate.opsForValue().get(cacheKey);
        if(viralityScore==null){
            viralityScore="0";
        }
        return Integer.parseInt(viralityScore);
      
    }
}
