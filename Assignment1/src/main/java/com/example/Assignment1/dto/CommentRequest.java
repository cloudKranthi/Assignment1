package com.example.Assignment1.dto;

import java.util.UUID;

public record CommentRequest(
    String postTitle, 
    String authorName, 
    String authorType, // "USER" or "BOT"
    String content, 
    UUID parentId
) {}