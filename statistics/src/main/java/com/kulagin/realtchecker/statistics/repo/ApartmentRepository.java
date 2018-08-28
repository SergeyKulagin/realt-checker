package com.kulagin.realtchecker.statistics.repo;

import com.kulagin.realtchecker.core.model.Apartment;
import com.kulagin.realtchecker.statistics.model.MongoApartment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApartmentRepository {
  private final String collectionName;
  private final MongoTemplate mongoTemplate;

  public ApartmentRepository(
      @Value("${spring.data.mongodb.apartments.collection}") String collectionName,
      MongoTemplate mongoTemplate
  ) {
    this.collectionName = collectionName;
    this.mongoTemplate = mongoTemplate;
  }

  public void store(List<Apartment> apartments) {
    for (Apartment apartment : apartments) {
      final MongoApartment mongoApartment = new MongoApartment();
      mongoApartment.setApartment(apartment);
      mongoTemplate.insert(mongoApartment, collectionName);
    }
  }

  public List<MongoApartment> searchByAddress(String term) {
    Query q = new Query();
    q.addCriteria(
        TextCriteria
            .forDefaultLanguage()
            .matching(term)
    );
    return mongoTemplate.find(q, MongoApartment.class, collectionName);
  }
}
