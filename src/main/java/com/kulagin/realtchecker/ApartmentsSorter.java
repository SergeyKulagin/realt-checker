package com.kulagin.realtchecker;

import com.kulagin.realtchecker.model.Apartment;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@Log4j2
public class ApartmentsSorter {
  public List<Apartment> sort(List<Apartment> apartments) {
    log.info("Sort apartments");
    apartments.sort(Comparator.comparing((apartment -> apartment.getLocation().getAddress())));
    return apartments;
  }
}
