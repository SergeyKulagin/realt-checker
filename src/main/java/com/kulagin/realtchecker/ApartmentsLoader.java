package com.kulagin.realtchecker;

import com.kulagin.realtchecker.model.Apartment;

import java.util.List;

public interface ApartmentsLoader {
  List<Apartment> load();
}
