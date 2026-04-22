package com.example.Assignment1.service;
import java.time.Duration;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
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
    public  void CreateComment(CommentEntity comment){
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
               String notificationKey="user:"+comment.getParentId()+":pending_notifs";
               String cooldownKey="user:"+comment.getParentId()+":cooldown";
               Boolean isCooled=redisTemplate.hasKey(cooldownKey);
               if(Boolean.TRUE.equals(isCooled)){
               redisTemplate.opsForList().rightPush(notificationKey,"Bot "+comment.getAuthourId().toString()+"replied to your post");
               }else{
               System.out.println("Bot pushed notification");
               redisTemplate.opsForValue().set(cooldownKey,"active",Duration.ofMinutes(15));
            }
        }
            else{
                count=10;
                System.out.println("Human comment added, increasing virality score by 10");
            }
            redisTemplate.opsForValue().increment(cacheKey,count);
    }
    @Scheduled(fixedRate=300000)
    public void sweep(){
       Set<String> keys=redisTemplate.keys("user:*:pending_notifs");
       if(keys!=null){
        for(String key:keys){
            Long ListSize =redisTemplate.opsForList().size(key);
            if(ListSize!=null && ListSize>0){
                String firstMessage=redisTemplate.opsForList().index(key, 0);
                System.out.println("Sending notification to user: "+key+" with message: "+firstMessage);
                redisTemplate.delete(key);
            }
        }
       }
    }
    
}
