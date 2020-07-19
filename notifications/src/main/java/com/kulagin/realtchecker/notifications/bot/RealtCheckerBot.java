package com.kulagin.realtchecker.notifications.bot;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class RealtCheckerBot extends TelegramLongPollingBot {
    private final Map<String, TelegramUpdateHandler> updateHandlers;
    @Value("${service.bot.token}")
    private String botToken;
    
    @Override
    public String getBotToken() {
        return botToken;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received update {}", update);
        Optional
                .ofNullable(update.getMessage())
                .map(Message::getText)
                .map(c -> updateHandlers.get(c))
                .ifPresentOrElse(h -> h.handle(update), () -> log.info("no handler"));
    }
    
    @Override
    public String getBotUsername() {
        return "realtCheckerBot";
    }
}
