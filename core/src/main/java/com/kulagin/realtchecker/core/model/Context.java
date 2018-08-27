package com.kulagin.realtchecker.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;

@Data
@ToString
public class Context {
  private Date date;
  private List<Apartment> previousApartments;
  private List<Apartment> apartments;
  private String htmlReportPath;

  private CompareApartmentResult compareApartmentResult;
}
