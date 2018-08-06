package com.kulagin.realtchecker;

import com.kulagin.realtchecker.model.Context;

public interface ApartmentsStorer {
  void store(Context context);

  void loadPreviousApartments(Context context);
}
