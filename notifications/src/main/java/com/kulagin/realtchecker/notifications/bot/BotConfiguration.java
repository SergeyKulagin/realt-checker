package com.kulagin.realtchecker.notifications.bot;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@Configuration
public class BotConfiguration {
    @Bean
    public RealtCheckerBot realtCheckerBot(List<TelegramUpdateHandler> handlers) throws Exception {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        RealtCheckerBot realtCheckerBot = new RealtCheckerBot(telegramUpdateHandlers(handlers));
        botsApi.registerBot(realtCheckerBot);
        return realtCheckerBot;
    }
    
    @Bean
    public Map<String, TelegramUpdateHandler> telegramUpdateHandlers(List<TelegramUpdateHandler> handlers) {
        return handlers
                .stream()
                .collect(Collectors.toMap(TelegramUpdateHandler::command, Function.identity()));
    }
}
