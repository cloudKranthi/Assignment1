package com.example.Assignment1.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex){
        Map<String,Object>body=new HashMap<>();
        body.put("requestId",UUID.randomUUID().toString());
        body.put("timestamp",LocalDateTime.now());
        body.put("message",ex.getMessage());
        body.put("status",ex.getHttpStatus().value());
        body.put("error",ex.getHttpStatus().getReasonPhrase());
        log.error("Business exception occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(body, ex.getHttpStatus());
    }
}
