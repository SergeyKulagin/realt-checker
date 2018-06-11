package com.kulagin.realtchecker.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApartmentPretty {
  private String address;
  private String price;
  private String url;
  private boolean lastFloor;
  private Area area;
}
