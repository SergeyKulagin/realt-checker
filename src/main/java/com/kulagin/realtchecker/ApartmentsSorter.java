package com.kulagin.realtchecker;

import com.kulagin.realtchecker.model.Apartment;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class ApartmentsSorter {
  public List<Apartment> sort(List<Apartment> apartments) {
    apartments.sort(Comparator.comparing((apartment -> apartment.getLocation().getAddress())));
    return apartments;
  }
}
