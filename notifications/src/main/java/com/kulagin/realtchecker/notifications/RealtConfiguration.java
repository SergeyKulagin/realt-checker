package com.kulagin.realtchecker.notifications;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.kulagin.realtchecker.core.ApartmentsComparer;
import com.kulagin.realtchecker.core.ApartmentsSorter;
import com.kulagin.realtchecker.core.SourceType;
import com.kulagin.realtchecker.core.impl.ApartmentsLoaderRealtBy;
import com.kulagin.realtchecker.notifications.db.ApartmentsStorerMongo;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RealtConfiguration {
    private final MongoTemplate mongoTemplate;
    
    @Bean
    @ConditionalOnProperty(value = "service.realtby.enabled", havingValue = "true", matchIfMissing = true)
    public Checker realtChecker(
            ApartmentsLoaderRealtBy apartmentsLoader,
            ApartmentsSorter apartmentsSorter,
            @Qualifier("apartmentsStorerRealt") ApartmentsStorerMongo apartmentsStorer,
            ApartmentsPrettyPrinterHTML apartmentsPrettyPrinter,
            ApartmentsNotifier apartmentsNotifier,
            ApartmentsComparer apartmentsComparer) {
        return new Checker(
                apartmentsLoader,
                apartmentsSorter,
                apartmentsStorer,
                apartmentsPrettyPrinter,
                apartmentsNotifier,
                apartmentsComparer
        );
    }
    
    @Bean
    public ApartmentsStorerMongo apartmentsStorerRealt() {
        return new ApartmentsStorerMongo(mongoTemplate, SourceType.REALTBY);
    }
}
