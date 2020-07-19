package com.kulagin.realtchecker.notifications.db;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoSubscriberRepository extends MongoRepository<Subscriber, Long> {
}
