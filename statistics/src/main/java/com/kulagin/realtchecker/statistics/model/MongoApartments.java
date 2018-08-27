package com.kulagin.realtchecker.statistics.model;

import com.kulagin.realtchecker.core.model.Apartment;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class MongoApartments {
  @Id
  private String id;
  private List<Apartment> apartments;
}
