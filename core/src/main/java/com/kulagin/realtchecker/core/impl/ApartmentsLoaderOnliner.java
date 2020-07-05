package com.kulagin.realtchecker.core.impl;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.kulagin.realtchecker.core.ApartmentsLoader;
import com.kulagin.realtchecker.core.OnlinerConfigurationProperties;
import com.kulagin.realtchecker.core.model.Apartment;
import com.kulagin.realtchecker.core.model.Context;
import com.kulagin.realtchecker.core.model.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class ApartmentsLoaderOnliner implements ApartmentsLoader {
  private final OnlinerConfigurationProperties queryConfiguration;

  @Override
  public void load(Context context) {
    log.info("Loading apartments with the following configuration {}", queryConfiguration);
    final RestTemplate restTemplate = new RestTemplate();
    int page = 1;
    List<Apartment> apartments = new ArrayList<>();
    while (true) {
      final String uri = fromHttpUrl(queryConfiguration.getUrl())
          .queryParam(queryConfiguration.getBoundsLbLatParamName(), queryConfiguration.getBoundsLbLat())
          .queryParam(queryConfiguration.getBoundsLbLongParamName(), queryConfiguration.getBoundsLbLong())
          .queryParam(queryConfiguration.getBoundsRtLatParamName(), queryConfiguration.getBoundsRtLat())
          .queryParam(queryConfiguration.getBoundsRtLongParamName(), queryConfiguration.getBoundsRtLong())
          .queryParam(queryConfiguration.getWallingParamName(), queryConfiguration.getWalling().split(","))
          .queryParam(queryConfiguration.getPageParamName(), page)
          .queryParam(queryConfiguration.getPriceMinParamName(), queryConfiguration.getPriceMin())
          .queryParam(queryConfiguration.getPriceMaxParamName(), queryConfiguration.getPriceMax())
          .queryParam(queryConfiguration.getCurrencyParamName(), queryConfiguration.getCurrency())
          .build().toUriString();

      log.info("Request data, uri is {})", uri);
      ResponseEntity<Result> response = restTemplate.getForEntity(
          uri,
          Result.class
      );
      Result result = response.getBody();
      apartments.addAll(result.getApartments());
      if(result.getPage().getCurrent().equals(result.getPage().getLast())){
        break;
      }
      page++;
    }
    context.setApartments(apartments);
  }
}
