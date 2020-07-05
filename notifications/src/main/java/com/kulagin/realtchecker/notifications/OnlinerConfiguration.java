package com.kulagin.realtchecker.notifications;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kulagin.realtchecker.core.ApartmentsComparer;
import com.kulagin.realtchecker.core.ApartmentsSorter;
import com.kulagin.realtchecker.core.impl.ApartmentsLoaderOnliner;
import com.kulagin.realtchecker.notifications.db.ApartmentsStorerMongo;

@Configuration
public class OnlinerConfiguration {
    @Bean
    public Checker onlinerChecker(
                                         ApartmentsLoaderOnliner apartmentsLoader,
                                         ApartmentsSorter apartmentsSorter,
                                         ApartmentsStorerMongo apartmentsStorer,
                                         ApartmentsPrettyPrinter apartmentsPrettyPrinter,
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
}
