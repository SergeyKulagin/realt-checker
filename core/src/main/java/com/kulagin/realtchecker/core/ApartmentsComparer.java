package com.kulagin.realtchecker.core;

import com.kulagin.realtchecker.core.model.Apartment;
import com.kulagin.realtchecker.core.model.ApartmentsCompareItem;
import com.kulagin.realtchecker.core.model.CompareApartmentResult;
import com.kulagin.realtchecker.core.model.Context;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Log4j2
public class ApartmentsComparer {
  public void compare(Context context) {
    log.info("Compare old and new apartments lists");
    final List<Apartment> currentApartments = context.getApartments();
    final List<Apartment> previousApartments = context.getPreviousApartments();
    final Map<Integer, Apartment> previousApartmentsMap = previousApartments.stream().collect(Collectors.toMap((a -> a.getId()) , apartment -> apartment, ((u,v)-> u)));
    CompareApartmentResult compareApartmentResult = new CompareApartmentResult();
    final List<Apartment> newlyCreatedApartments = new ArrayList<>();
    final List<ApartmentsCompareItem> changedApartments = new ArrayList<>();
    for (Apartment currentApartment : currentApartments) {
      final Apartment previousApartment = previousApartmentsMap.remove(currentApartment.getId());
      if (previousApartment == null) {
        newlyCreatedApartments.add(currentApartment);
      } else {
        if (previousApartment.getPrice().getAmount().compareTo(currentApartment.getPrice().getAmount()) != 0) {
          final ApartmentsCompareItem apartmentsCompareItem = new ApartmentsCompareItem(
              previousApartment,
              currentApartment
          );
          changedApartments.add(apartmentsCompareItem);
        }
      }
    }

    compareApartmentResult.setNewlyCreatedApartments(newlyCreatedApartments);
    compareApartmentResult.setChangedApartments(changedApartments);
    compareApartmentResult.setDisappearedApartments(new ArrayList<>(previousApartmentsMap.values()));
    context.setCompareApartmentResult(compareApartmentResult);
  }
}
