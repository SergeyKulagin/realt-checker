package com.kulagin.realtchecker.core;


import java.util.List;

import com.kulagin.realtchecker.core.model.Apartment;

public interface ApartmentsLoader {
  List<Apartment> load();
}
