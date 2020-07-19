package com.kulagin.realtchecker.notifications;

import java.time.Instant;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import com.kulagin.realtchecker.core.model.Context;
import com.kulagin.realtchecker.notifications.bot.RealtCheckerBot;
import com.kulagin.realtchecker.notifications.db.SubscribersRepository;
import com.kulagin.realtchecker.notifications.db.TelegramMessage;
import com.kulagin.realtchecker.notifications.db.TelegramMessagesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class ApartmentsNotifier {
    private final RealtCheckerBot bot;
    private final SubscribersRepository subscribersRepository;
    private final TelegramMessagesRepository messagesRepository;
    
    public void notify(Context context) {
        var subscribers = subscribersRepository.get();
        log.info("Notify about changes in the flats following subscribers {}", subscribers);
        subscribers.stream().forEach((chatId) -> {
            try {
                if (context.getCompareApartmentResult().hasChanges()) {
                    save(bot.execute(new SendMessage().setChatId(chatId).setText(context.getChangesPrettyPrint())));
                    save(bot.execute(
                            new SendDocument()
                                    .setChatId(chatId)
                                    .setDocument(context.getPrettyPrintFile())
                                    .setParseMode(context.getPrettyPrintFormat())));
                } else {
                    save(bot.execute(new SendMessage()
                            .setChatId(chatId)
                            .setText("No changes were detected")));
                }
            } catch (TelegramApiRequestException e) {
                log.error("Api response error, code " + e.getErrorCode() + " description" + e.getApiResponse());
            } catch (TelegramApiException e) {
                log.error("Error sending message to telegram", e);
            }
        });
    }
    
    private void save(Message msg) {
        messagesRepository.save(TelegramMessage
                .builder()
                .id(msg.getMessageId())
                .date(Instant.ofEpochMilli(msg.getDate())).build());
    }
}
