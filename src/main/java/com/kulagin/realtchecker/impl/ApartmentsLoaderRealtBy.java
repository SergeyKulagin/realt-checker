package com.kulagin.realtchecker.impl;

import com.kulagin.realtchecker.ApartmentsLoader;
import com.kulagin.realtchecker.model.Apartment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class ApartmentsLoaderRealtBy implements ApartmentsLoader {

  @Value("${checker.query.realtby.search_hash}")
  private String searchHash;

  @Value("${checker.query.uri}")
  private String baseUrl;

  @Override
  public List<Apartment> load() {
    int page = 0;
    while (true) {
      final String url = UriComponentsBuilder
          .fromHttpUrl(baseUrl)
          .queryParam("search", searchHash)
          .build()
          .toString();

      final RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    }
  }
}
