package com.kulagin.realtchecker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kulagin.realtchecker.model.Apartment;
import com.kulagin.realtchecker.model.CompareApartmentResult;
import com.kulagin.realtchecker.model.Context;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Log4j2
public class ApartmentsComparer {
  public void compare(Context context) {
    final List<Apartment> currentApartments = context.getApartments();
    final List<Apartment> previousApartments = loadPrevious(context);
    final Map<Integer, Apartment> previousApartmentsMap = previousApartments.stream().collect(Collectors.toMap((a -> a.getId()), apartment -> apartment));
    CompareApartmentResult compareApartmentResult = new CompareApartmentResult();
    final List<Apartment> newlyCreatedApartments = new ArrayList<>();
    final List<Apartment> changedApartmentsPrevious = new ArrayList<>();
    final List<Apartment> changedApartmentsNew = new ArrayList<>();
    for (Apartment currentApartment : currentApartments) {
      final Apartment previousApartment = previousApartmentsMap.get(currentApartment.getId());
      if (previousApartment == null) {
        newlyCreatedApartments.add(currentApartment);
      } else {
        if (previousApartment.getPrice().getAmount().compareTo(currentApartment.getPrice().getAmount()) != 0) {
          changedApartmentsPrevious.add(previousApartment);
          changedApartmentsNew.add(currentApartment);
        }
      }
    }

    compareApartmentResult.setNewlyCreatedApartments(newlyCreatedApartments);
    compareApartmentResult.setChangedApartmentsPrevious(changedApartmentsPrevious);
    compareApartmentResult.setChangedApartmentsNew(changedApartmentsNew);
    context.setCompareApartmentResult(compareApartmentResult);
  }


  private List<Apartment> loadPrevious(Context context) {
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
