package com.kulagin.realtchecker.core.model;

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
