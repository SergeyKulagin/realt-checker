package com.kulagin.realtchecker.notifications.db;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApartmentRepository extends MongoRepository<Apartments, String> {
}
