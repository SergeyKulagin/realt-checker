package com.kulagin.realtchecker.notifications.db;

import java.time.Instant;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.kulagin.realtchecker.core.ApartmentsStorer;
import com.kulagin.realtchecker.core.model.Context;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ApartmentsStorerMongo implements ApartmentsStorer {
    private final ApartmentRepository apartmentRepository;
    
    @Override
    public void store(Context context) {
        apartmentRepository.save(Apartments
                .builder()
                .apartments(context.getApartments())
                .date(Instant.now())
                .build()
        );
    }
    
    @Override
    public void loadPreviousApartments(Context context) {
        apartmentRepository
                .findAll(PageRequest.of(0, 1, Sort.by("date").descending()))
                .get().findFirst()
                .ifPresent(a -> {
                    context.setApartments(a.getApartments());
                });
    }
}
