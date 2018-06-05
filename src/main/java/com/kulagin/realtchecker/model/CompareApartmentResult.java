package com.kulagin.realtchecker.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompareApartmentResult {
  private List<Apartment> newlyCreatedApartments;
  private List<Apartment> changedApartmentsPrevious;
  private List<Apartment> changedApartmentsNew;

  public boolean hasChanges() {
    return
        newlyCreatedApartments.size() > 0 ||
            changedApartmentsPrevious.size() > 0;
  }
}
