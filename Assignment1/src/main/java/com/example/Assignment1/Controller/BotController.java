package com.example.Assignment1.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Assignment1.model.BotEntity;
import com.example.Assignment1.service.BotService;

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/bots")
public class BotController {
    
    private final BotService botService;
    @PostMapping("/create")
    public ResponseEntity<?> createBot(@RequestBody BotEntity botEntity){
      try {
          botService.createBot(botEntity);
          return ResponseEntity.status(201).body(botEntity);
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while creating Bot: " + e.getMessage());
      }  
    }
}
