package com.kulagin.realtchecker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kulagin.realtchecker.model.Apartment;
import com.kulagin.realtchecker.model.ApartmentsCompareItem;
import com.kulagin.realtchecker.model.CompareApartmentResult;
import com.kulagin.realtchecker.model.Context;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Log4j2
public class ApartmentsComparer {
  public void compare(Context context) {
    log.info("Compare old and new apartments lists");
    final List<Apartment> currentApartments = context.getApartments();
    final List<Apartment> previousApartments = loadPrevious(context);
    final Map<Integer, Apartment> previousApartmentsMap = previousApartments.stream().collect(Collectors.toMap((a -> a.getId()), apartment -> apartment));
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


  private List<Apartment> loadPrevious(Context context) {
    //no previous data exist, return empty list
    if(StringUtils.isEmpty(context.getLastJsonReportPath())){
      return Collections.emptyList();
    }
    ObjectMapper objectMapper = new ObjectMapper();
    try (FileInputStream fis = new FileInputStream(context.getLastJsonReportPath().toFile())) {
      List<Apartment> apartments = objectMapper.readValue(fis, new TypeReference<List<Apartment>>() {
      });
      return apartments;
    } catch (IOException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
  }
}
