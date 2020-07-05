package com.kulagin.realtchecker.notifications;

import com.kulagin.realtchecker.core.ApartmentInitialContextLoader;
import com.kulagin.realtchecker.core.ApartmentsComparer;
import com.kulagin.realtchecker.core.ApartmentsLoader;
import com.kulagin.realtchecker.core.ApartmentsSorter;
import com.kulagin.realtchecker.core.ApartmentsStorer;
import com.kulagin.realtchecker.core.model.Context;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
public class Checker {
    private final ApartmentsLoader apartmentsLoader;
    private final ApartmentsSorter apartmentsSorter;
    private final ApartmentsStorer apartmentsStorer;
    private final ApartmentsPrettyPrinter apartmentsPrettyPrinter;
    private final ApartmentsNotifier apartmentsNotifier;
    private final ApartmentsComparer apartmentsComparer;
    
    public void check() {
        Context context = new ApartmentInitialContextLoader(apartmentsStorer).loadContext();
        apartmentsLoader.load(context);
        apartmentsSorter.sort(context);
        apartmentsStorer.store(context);
        apartmentsComparer.compare(context);
        apartmentsNotifier.notify(context);
    }
}
