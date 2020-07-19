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
    private final OnlinerConfigurationProperties configuration;
    
    @Override
    public void load(Context context) {
        log.info("Loading apartments with the following configuration {}", configuration);
        final RestTemplate restTemplate = new RestTemplate();
        int page = 1;
        List<Apartment> apartments = new ArrayList<>();
        while (true) {
            final String uri = fromHttpUrl(configuration.getUrl())
                    .queryParam(configuration.getBoundsLbLatParamName(), configuration.getBoundsLbLat())
                    .queryParam(configuration.getBoundsLbLongParamName(), configuration.getBoundsLbLong())
                    .queryParam(configuration.getBoundsRtLatParamName(), configuration.getBoundsRtLat())
                    .queryParam(configuration.getBoundsRtLongParamName(), configuration.getBoundsRtLong())
                    .queryParam(configuration.getWallingParamName(), configuration.getWalling().split(","))
                    .queryParam(configuration.getPageParamName(), page)
                    .queryParam(configuration.getPriceMinParamName(), configuration.getPriceMin())
                    .queryParam(configuration.getPriceMaxParamName(), configuration.getPriceMax())
                    .queryParam(configuration.getCurrencyParamName(), configuration.getCurrency())
                    .build().toUriString();
            
            log.info("Request data, uri is {})", uri);
            ResponseEntity<Result> response = restTemplate.getForEntity(
                    uri,
                    Result.class
            );
            Result result = response.getBody();
            apartments.addAll(result.getApartments());
            if (result.getPage().getCurrent().equals(result.getPage().getLast())) {
                break;
            }
            page++;
            log.info("Sleep between queries for {} ms", configuration.getQueryDelay().toMillis());
            try {
                Thread.sleep(configuration.getQueryDelay().toMillis());
            } catch (InterruptedException e) {
                log.error("Interrupted exception on sleep query delay", e);
            }
        }
        context.setApartments(apartments);
    }
}
