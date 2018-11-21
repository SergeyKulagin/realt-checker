package com.kulagin.realtchecker.statistics;

import com.samskivert.mustache.Mustache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mustache.MustacheEnvironmentCollector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;

import javax.annotation.PostConstruct;

@Configuration
public class StatisticsConfig {
  public static final String ADDRESS_TEXT_INDEX = "apartment.location.address";
  private final MongoTemplate mongoTemplate;
  private final String apartmentsCollectionName;

  public StatisticsConfig(
      MongoTemplate mongoTemplate,
      @Value("${spring.data.mongodb.apartments.collection}") String apartmentsCollectionName) {
    this.mongoTemplate = mongoTemplate;
    this.apartmentsCollectionName = apartmentsCollectionName;
  }

  @PostConstruct
  public void initIndexes() {
    mongoTemplate
        .indexOps(apartmentsCollectionName)
        .ensureIndex(
            TextIndexDefinition
                .builder()
                .onField(ADDRESS_TEXT_INDEX)
                .build()
        );
  }
}
