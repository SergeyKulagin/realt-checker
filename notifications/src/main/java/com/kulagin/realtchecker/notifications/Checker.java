package com.kulagin.realtchecker.notifications;

import java.util.List;

import com.kulagin.realtchecker.core.ApartmentInitialContextLoader;
import com.kulagin.realtchecker.core.ApartmentsComparer;
import com.kulagin.realtchecker.core.ApartmentsLoader;
import com.kulagin.realtchecker.core.ApartmentsSorter;
import com.kulagin.realtchecker.core.ApartmentsStorer;
import com.kulagin.realtchecker.core.model.Apartment;
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
    //private final TaskScheduler taskScheduler;
    
    public void check() {
        final Context context = new ApartmentInitialContextLoader(apartmentsStorer)
                .loadContext();
        final List<Apartment> apartmentList = apartmentsLoader.load();
        context.setApartments(apartmentList);
        apartmentsSorter.sort(context);
        apartmentsStorer.store(context);
        //apartmentsPrettyPrinter.printReport(context);
        //apartmentsComparer.compare(context);
        //apartmentsNotifier.notify(context);
    }
}
