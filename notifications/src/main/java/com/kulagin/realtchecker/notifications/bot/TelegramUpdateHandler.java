package com.kulagin.realtchecker.notifications.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramUpdateHandler {
    void handle(Update update);
    String command();
}
