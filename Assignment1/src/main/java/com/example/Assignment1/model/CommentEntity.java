package com.example.Assignment1.model;

import java.time.LocalDateTime;
import java.util.*;
import java.util.UUID;
import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CommentEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;
    private UUID postId;//To know about which post we are talking 
    private UUID authourId;//To know which authour we are talking
    private UUID parentId;//To know which parent we are talking 
    private String authorType;//To know weather authour is BOT or USER
    @Column(columnDefinition="TEXT")
    private String content;
    private Integer depthLevel;
    @Column(nullable=false,updatable=false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
