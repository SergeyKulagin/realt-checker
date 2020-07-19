package com.kulagin.realtchecker.notifications.db;

import java.time.Instant;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;

import com.kulagin.realtchecker.core.ApartmentsStorer;
import com.kulagin.realtchecker.core.SourceType;
import com.kulagin.realtchecker.core.model.Context;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApartmentsStorerMongo implements ApartmentsStorer {
    private final MongoTemplate mongoTemplate;
    private final SourceType sourceType;
    
    @Override
    public void store(Context context) {
        mongoTemplate.save(
                Apartments
                        .builder()
                        .apartments(context.getApartments())
                        .date(Instant.now())
                        .build(), sourceType.getApartmentsCollection());
    }
    
    @Override
    public void loadPreviousApartments(Context context) {
        mongoTemplate
                .query(Apartments.class)
                .inCollection(sourceType.getApartmentsCollection())
                .matching(
                        new BasicQuery("{}")
                                .with(PageRequest.of(0, 1, Sort.by("date").descending()))
                )
                .first()
                .ifPresent(a -> context.setPreviousApartments(a.getApartments()));
    }
}
