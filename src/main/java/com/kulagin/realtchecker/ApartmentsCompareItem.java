package com.kulagin.realtchecker;

import com.kulagin.realtchecker.model.Apartment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApartmentsCompareItem {
  private Apartment previousApartment;
  private Apartment newApartment;
}
