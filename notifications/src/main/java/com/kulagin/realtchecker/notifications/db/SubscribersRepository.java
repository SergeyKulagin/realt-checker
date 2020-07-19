package com.kulagin.realtchecker.notifications.db;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubscribersRepository {
    private final MongoSubscriberRepository mongoSubscriberRepository;
    
    public void add(long id) {
        mongoSubscriberRepository
                .findById(id)
                .or(() -> Optional.of(mongoSubscriberRepository.save(
                        Subscriber.builder()
                                .chatId(id)
                                .date(Instant.now())
                                .build()
                )));
    }
    
    public Set<Long> get() {
        return mongoSubscriberRepository
                .findAll().stream().map(Subscriber::getChatId)
                .collect(Collectors.toSet());
    }
}
