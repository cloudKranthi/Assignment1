package com.example.Assignment1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Assignment1.Repository.BotRepository;
import com.example.Assignment1.Repository.CommentRepository;
import com.example.Assignment1.model.CommentEntity;
@Service
public class CommentService {
    
    @Autowired
    public BotRepository botrepository;
    @Autowired
    public GuardRailService guardRailService;
    @Autowired
    public CommentRepository commentRepository;
    @Autowired
    public StringRedisTemplate redisTemplate;
    @Transactional
    public  void CommetRepository(CommentEntity comment){
        int depth=0;
        if(comment.getParentId()!=null){
            CommentEntity parentComment = commentRepository.findById(comment.getParentId()).orElse(null);
            if (parentComment != null) {
                depth = parentComment.getDepthLevel() + 1;
            }
            comment.setDepthLevel(depth);
        }
        guardRailService.checkCommonGuardRailService(comment.getPostId(),comment.getAuthourId(),depth,comment.getParentId(),comment.getAuthorType());
        commentRepository.save(comment);
        String cacheKey="post"+comment.getPostId().toString()+":virality_score";
        Integer count=0;
            if("BOT".equalsIgnoreCase(comment.getAuthorType())){
               count=1;
            }
            else{
                count=10;
            }
            redisTemplate.opsForValue().increment(cacheKey,count);
    }
    
}
