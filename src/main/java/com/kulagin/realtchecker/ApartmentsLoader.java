package com.kulagin.realtchecker;

import com.kulagin.realtchecker.model.Apartment;
import com.kulagin.realtchecker.model.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Component
@Log4j2
public class ApartmentsLoader {
  private final CheckerQueryConfiguration queryConfiguration;

  public ApartmentsLoader(CheckerQueryConfiguration checkerQueryConfiguration) {
    this.queryConfiguration = checkerQueryConfiguration;
  }

  public List<Apartment> load() {
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
          .queryParam(queryConfiguration.getWallingParamName(), queryConfiguration.getWalling())
          .queryParam(queryConfiguration.getPageParamName(), page)
          .queryParam(queryConfiguration.getPriceMinParamName(), queryConfiguration.getPriceMin())
          .queryParam(queryConfiguration.getPriceMaxParamName(), queryConfiguration.getPriceMax())
          .queryParam(queryConfiguration.getCurrencyParamName(), queryConfiguration.getCurrency())
          .build().toUriString();
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
    return apartments;
  }
}
