package com.kulagin.realtchecker.core;

import com.kulagin.realtchecker.core.model.Context;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
@AllArgsConstructor
public class ApartmentInitialContextLoader {

  private final ApartmentsStorer apartmentsStorer;

  public Context loadContext() {
    final Context context = new Context();
    context.setDate(new Date());
    apartmentsStorer.loadPreviousApartments(context);
    log.info("Loading context {}", context);
    return context;
  }
}
