package com.kulagin.realtchecker.statistics.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kulagin.realtchecker.core.model.Apartment;
import com.kulagin.realtchecker.statistics.model.MongoApartments;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class ApartmentRepository {
  public static final String COLLECTION_NAME = "apartments";
  private final MongoDbFactory mongoDbFactory;
  private final ObjectMapper objectMapper;

  public void store(List<Apartment> apartments) {
    final MongoDatabase mongo = mongoDbFactory.getDb();
    final MongoCollection apartmentsCollection = mongo.getCollection(COLLECTION_NAME);
    final MongoApartments mongoApartments = new MongoApartments();
    mongoApartments.setApartments(apartments);
    try {
      apartmentsCollection.insertOne(Document.parse(objectMapper.writeValueAsString(mongoApartments)));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }


  public List<Apartment> getPreviousApartments() {
    final MongoDatabase mongo = mongoDbFactory.getDb();
    final MongoCollection<MongoApartments> apartmentsCollection = mongo.getCollection(COLLECTION_NAME, MongoApartments.class);
    final MongoApartments last = apartmentsCollection
        .find()
        .sort(Sorts.descending("_id"))
        .limit(1)
        .first();

    if (last == null) {
      return Collections.emptyList();
    } else {
      return last.getApartments();
    }
  }
}
