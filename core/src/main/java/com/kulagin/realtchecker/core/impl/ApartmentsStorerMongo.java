package com.kulagin.realtchecker.core.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kulagin.realtchecker.core.ApartmentsStorer;
import com.kulagin.realtchecker.core.model.Context;
import com.kulagin.realtchecker.core.repo.ApartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier("mongo")
@Component
@AllArgsConstructor
public class ApartmentsStorerMongo implements ApartmentsStorer {

  private final ApartmentRepository apartmentRepository;

  @Override
  public void store(Context context) {
     apartmentRepository.store(context.getApartments());
  }

  @Override
  public void loadPreviousApartments(Context context) {
    context.setPreviousApartments(apartmentRepository.getPreviousApartments());
  }
}
