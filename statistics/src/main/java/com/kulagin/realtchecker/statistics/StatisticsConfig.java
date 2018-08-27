package com.kulagin.realtchecker.statistics;

import com.kulagin.realtchecker.statistics.repo.ApartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

import javax.annotation.PostConstruct;

@Configuration
@AllArgsConstructor
public class StatisticsConfig {
  private MongoTemplate mongoTemplate;

  @PostConstruct
  public void initIndexes() {
    mongoTemplate
        .indexOps(ApartmentRepository.COLLECTION_NAME)
        .ensureIndex(new Index().on("apartments.location.address", Sort.Direction.ASC));
  }
}
