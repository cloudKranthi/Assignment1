package com.example.Assignment1.service;
import com.example.Assignment1.Repository.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Assignment1.model.BotEntity;
@Service
public class BotService {
    @Autowired
    private BotRepository botRepository;
    public void createBot(BotEntity bot) {
        botRepository.save(bot);
    }
}
