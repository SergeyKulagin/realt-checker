package com.kulagin.realtchecker.notifications.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.kulagin.realtchecker.notifications.db.SubscribersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RealtUpdatesStartHandler implements TelegramUpdateHandler {
    private final SubscribersRepository subscribersRepository;
    
    @Override
    public void handle(Update update) {
        var chatId = update.getMessage().getChatId();
        log.info("Subscribe following chat id {}", chatId);
        subscribersRepository.add(chatId);
    }
    
    @Override
    public String command() {
        return "/updates/start";
    }
}
