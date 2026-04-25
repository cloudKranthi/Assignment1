package com.example.Assignment1.dto;

import lombok.NonNull;

public record PostResponse(@NonNull String postTitle ,@NonNull String content,@NonNull String authorName,@NonNull String authorType) {}