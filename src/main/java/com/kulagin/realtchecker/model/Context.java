package com.kulagin.realtchecker.model;

import lombok.Data;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;

@Data
public class Context {
  private Date date;
  private List<Apartment> apartments;
  private String htmlReportPath;
  private Path lastJsonReportPath;

  private CompareApartmentResult compareApartmentResult;
}
