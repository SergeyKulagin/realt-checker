package com.kulagin.realtchecker;

import com.kulagin.realtchecker.model.Apartment;
import com.kulagin.realtchecker.model.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Component
public class ApartmentsLoader {
  private final CheckerQueryConfiguration queryConfiguration;

  public ApartmentsLoader(CheckerQueryConfiguration checkerQueryConfiguration) {
    this.queryConfiguration = checkerQueryConfiguration;
  }

  public List<Apartment> load() {
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
          .queryParam("page", page)
          .queryParam("price[min]", queryConfiguration.getPriceMin())
          .queryParam("price[max]", queryConfiguration.getPriceMax())
          .queryParam("currency", queryConfiguration.getCurrency())
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