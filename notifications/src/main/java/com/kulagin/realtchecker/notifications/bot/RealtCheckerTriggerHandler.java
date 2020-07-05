package com.kulagin.realtchecker.notifications.bot;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RealtCheckerTriggerHandler implements TelegramUpdateHandler{
    private final ApplicationContext applicationContext;
    
    @Override
    public void handle(Update update) {
        applicationContext.publishEvent(new TriggerCheckerEvent(update));
    }
    
    @Override
    public String command() {
        return "/updates/trigger";
    }
}
