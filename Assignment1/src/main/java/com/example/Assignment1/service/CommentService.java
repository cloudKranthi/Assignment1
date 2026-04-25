package com.example.Assignment1.service;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import com.example.Assignment1.Repository.PostRepository;
import com.example.Assignment1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Assignment1.Repository.BotRepository;
import com.example.Assignment1.Repository.CommentRepository;
import com.example.Assignment1.dto.CommentRequest;
import com.example.Assignment1.model.CommentEntity;
import com.example.Assignment1.model.PostEntity;
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
    @Autowired
    public PostRepository postRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public BotRepository botRepository;
    @Autowired
    public ViralityService viralityService;
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
                count=20;
                System.out.println("Human comment added, increasing virality score by 20");
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
    public CommentEntity mapToEntity(CommentRequest dto) {
    // 1. Resolve IDs from Names
    PostEntity post = postRepository.findByPostTitle(dto.postTitle());
     
        
    UUID authorId;
    if ("USER".equalsIgnoreCase(dto.authorType())) {
        authorId = userRepository.findByUsername(dto.authorName())
            .orElseThrow(() -> new RuntimeException("User not found"))
            .getId();
    } else {
        authorId = botRepository.findByName(dto.authorName())
            .orElseThrow(() -> new RuntimeException("Bot not found"))
            .getId();
    }

    
    CommentEntity entity = new CommentEntity();
    entity.setPostId(post.getId());
    entity.setAuthourId(authorId);
    entity.setAuthorType(dto.authorType());
    entity.setContent(dto.content());
    entity.setParentId(dto.parentId());
    
    return entity;
}
    
}
