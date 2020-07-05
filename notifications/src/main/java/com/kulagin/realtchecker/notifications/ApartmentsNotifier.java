package com.kulagin.realtchecker.notifications;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.kulagin.realtchecker.core.model.Context;
import com.kulagin.realtchecker.notifications.bot.RealtCheckerBot;
import com.kulagin.realtchecker.notifications.db.SubscribersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class ApartmentsNotifier {
    private final RealtCheckerBot bot;
    private final SubscribersRepository subscribersRepository;
    
    public void notify(Context context) {
        log.info("Notify about changes in the flats");
        subscribersRepository.get().stream().forEach((chatId) -> {
            try {
                bot.execute(new SendMessage()
                        .setChatId(chatId)
                        .setText("There are changes in apartments"));
            } catch (TelegramApiException e) {
                log.error("Error sending message to telegram", e);
            }
        });
    }
}
