package com.kulagin.realtchecker.core.impl;

import com.kulagin.realtchecker.core.CheckerQueryConfiguration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@RequiredArgsConstructor
@Component
public class RealtBySearchHashProvider {
    private final RestTemplate restTemplate;
    private final CheckerQueryConfiguration queryConfiguration;
    private final QueryParameters queryParameters;

    public String get() {
        //1. get page to get the secret-hash
        ResponseEntity<String> page = restTemplate.getForEntity(
                queryParameters.getParameters().get("url"),
                String.class
        );
        final Document pageWithHash = Jsoup.parse(page.getBody());
        final Element hashElement = pageWithHash.getElementById("secret-hash");
        final String hash = hashElement.attr("value");
        //2. get search hash
        UriComponentsBuilder uriComponentsBuilder = fromHttpUrl(queryConfiguration.getUrl());
        uriComponentsBuilder.queryParam("hash", hash);
        queryParameters.getParameters().forEach((k,v)-> uriComponentsBuilder.queryParam(k,v));
        final String seachHashUri = uriComponentsBuilder.build().toUriString();
        ResponseEntity<HashSearchResult> hashSearchResultResponse = restTemplate.getForEntity(seachHashUri, HashSearchResult.class);
        HashSearchResult hashSearchResult = hashSearchResultResponse.getBody();
        final String searchForHash =  hashSearchResult.search;
        return searchForHash;
    }

    @Getter
    @Setter
    public static class HashSearchResult {
        private int count;
        private String search;
    }
}
