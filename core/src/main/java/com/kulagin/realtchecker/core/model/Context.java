package com.kulagin.realtchecker.core.model;

import java.io.File;
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
  private File prettyPrintFile;
  private String prettyPrintFormat;
  private String changesPrettyPrint;
  
  private CompareApartmentResult compareApartmentResult;
}
