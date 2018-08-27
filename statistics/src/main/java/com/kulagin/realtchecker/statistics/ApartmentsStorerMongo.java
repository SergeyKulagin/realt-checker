package com.kulagin.realtchecker.statistics;

import com.kulagin.realtchecker.core.ApartmentsStorer;
import com.kulagin.realtchecker.core.model.Context;
import com.kulagin.realtchecker.statistics.repo.ApartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
