package com.kulagin.realtchecker.core;


import com.kulagin.realtchecker.core.model.Apartment;

import java.util.List;

public interface ApartmentsLoader {
  List<Apartment> load();
}
