package com.kulagin.realtchecker.notifications.bot;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BotConfiguration  {
    private final List<TelegramUpdateHandler> handlers;
    
    @PostConstruct
    public void init() throws Exception{
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(realtCheckerBot());
    }
    
    @Bean
    public Map<String, TelegramUpdateHandler> telegramUpdateHandlers(List<TelegramUpdateHandler> handlers) {
        return handlers
                .stream()
                .collect(Collectors.toMap(TelegramUpdateHandler::command, Function.identity()));
    }
    
    @Bean
    public RealtCheckerBot realtCheckerBot(){
        return new RealtCheckerBot(telegramUpdateHandlers(handlers));
    }
}
