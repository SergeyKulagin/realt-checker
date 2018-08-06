package com.kulagin.realtchecker;

import com.kulagin.realtchecker.impl.ApartmentsStorerFileSystem;
import com.kulagin.realtchecker.model.Context;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class ApartmentInitialContextLoader {

  private final ApartmentsStorer apartmentsStorer;

  public ApartmentInitialContextLoader(@Qualifier("filesystem")ApartmentsStorer apartmentsStorer) {
    this.apartmentsStorer = apartmentsStorer;
  }

  public Context loadContext() {
    final Context context = new Context();
    context.setDate(new Date());
    apartmentsStorer.loadPreviousApartments(context);
    log.info("Loading context {}", context);
    return context;
  }
}
