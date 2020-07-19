package com.kulagin.realtchecker.notifications.db;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TelegramMessagesRepository {
    private final MongoTelegramMessagesRepository repository;
    
    public void save(TelegramMessage message) {
        repository.save(message);
    }
}
