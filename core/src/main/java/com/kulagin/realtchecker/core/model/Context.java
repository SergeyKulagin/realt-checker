package com.kulagin.realtchecker.core.model;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Context {
  private Date date;
  private List<Apartment> previousApartments;
  private List<Apartment> apartments;
  private String htmlReportPath;

  private CompareApartmentResult compareApartmentResult;
}
