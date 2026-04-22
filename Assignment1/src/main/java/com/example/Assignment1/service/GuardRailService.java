package com.example.Assignment1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
@Service
public class GuardRailService {
    @Autowired
    private StringRedisTemplate RedisTemplate;
    public void checkCommonGuardRailService(UUID postid,UUID  bootid,int depth,UUID humanid,String AuthourType){
       VerticalCheck(depth);
        if("BOT".equalsIgnoreCase(AuthourType)){
        String Key="post"+postid.toString()+":botcount";
        checkHorizontalCap(Key);
        String lockKey="lock-bot"+bootid+"human-id"+humanid;
        LockCheck(lockKey);
    }
    
}
public void checkHorizontalCap(String Key){
    Long count=RedisTemplate.opsForValue().increment(Key, 1);
    if(count!=null&&count>100){
        throw new RuntimeException("Too Many Requests");
    }
}
    public void LockCheck(String lockkey){
        Boolean isLocked=RedisTemplate.opsForValue().setIfAbsent(lockkey,"active",Duration.ofMinutes(10));
        if(Boolean.FALSE.equals(isLocked)){
            throw new RuntimeException("Interaction Lock: This bot must wait 10 minutes to reply to this user again.");
        }
    }
    public void VerticalCheck(int depth){
        if(depth>20){
            throw new RuntimeException("Depth Limit Exceeded Comments cannot be nested more than 20 levels deep.");
        }
    }
}

