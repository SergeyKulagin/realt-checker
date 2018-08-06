package com.kulagin.realtchecker.model;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class MongoApartments {
  @Id
  private String id;
  private List<Apartment> apartments;
}
