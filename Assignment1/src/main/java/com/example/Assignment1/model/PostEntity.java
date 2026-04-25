package com.example.Assignment1.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PostEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;
    private UUID authorId;
    @Column(unique=true,nullable=false)
    private String postTitle;
    private String content;
    private String authorType;
    private Integer BotReplies;
    @CreationTimestamp
    @Column(updatable=false,nullable=false)
    private LocalDateTime createdAt;
}

