package com.kulagin.realtchecker.statistics.model;

import com.kulagin.realtchecker.core.model.Apartment;
import lombok.Data;

import java.util.Date;

@Data
public class MongoApartment {
  private Date created = new Date();
  private Apartment apartment;
}
