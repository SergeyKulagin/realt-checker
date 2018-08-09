package com.kulagin.realtchecker.core;

import com.kulagin.realtchecker.core.model.Context;

public interface ApartmentsStorer {
  void store(Context context);

  void loadPreviousApartments(Context context);
}
