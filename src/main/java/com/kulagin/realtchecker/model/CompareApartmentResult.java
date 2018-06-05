package com.kulagin.realtchecker.model;

import com.kulagin.realtchecker.ApartmentsCompareItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompareApartmentResult {
  private List<Apartment> newlyCreatedApartments;
  private List<ApartmentsCompareItem> changedApartments;

  public boolean hasChanges() {
    return
        newlyCreatedApartments.size() > 0 ||
            changedApartments.size() > 0;
  }
}
