package com.kulagin.realtchecker;

import com.kulagin.realtchecker.model.Context;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@Log4j2
public class ApartmentsSorter {
  public void sort(Context context) {
    log.info("Sort apartments");
    context.getApartments().sort(Comparator.comparing((apartment -> apartment.getLocation().getAddress())));
  }
}
