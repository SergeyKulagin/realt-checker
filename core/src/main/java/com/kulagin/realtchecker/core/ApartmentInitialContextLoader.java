package com.kulagin.realtchecker.core;

import java.util.Date;

import com.kulagin.realtchecker.core.model.Context;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
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
