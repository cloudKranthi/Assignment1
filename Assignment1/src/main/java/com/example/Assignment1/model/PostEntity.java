package com.example.Assignment1.model;

import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PostEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;
    private UUID author_id;
    private String content;
    private String authorType;
    private Integer BotReplies;
    @CreationTimestamp
    @Column(updatable=false,nullable=false)
    private LocalDateTime createdAt;
}

