package com.kulagin.realtchecker.core.impl;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class RealtBySearchHashProvider {
    private final RestTemplate restTemplate;
    private final QueryParameters queryParameters;

    public String get() {
        //1. get page to get the secret-hash
        ResponseEntity<String> page = restTemplate.exchange(
                queryParameters.getUrls().get("page-hash-url"),
                HttpMethod.GET,
                entity(),
                String.class
        );
        final Document pageWithHash = Jsoup.parse(page.getBody());
        final Element hashElement = pageWithHash.getElementById("secret-hash");
        final String hash = hashElement.attr("value");
        //2. get search hash
        UriComponentsBuilder uriComponentsBuilder = fromHttpUrl(queryParameters.getUrls().get("get-hash-url"));
        uriComponentsBuilder.queryParam("hash", hash);
        queryParameters.getParameters().forEach((k,v)-> uriComponentsBuilder.queryParam(k,v.split(",")));
        final String seachHashUri = uriComponentsBuilder.build().toUriString();
        log.info("get-hash-url is {}", seachHashUri);
        ResponseEntity<HashSearchResult> hashSearchResultResponse = restTemplate.exchange(seachHashUri, HttpMethod.GET, entity(), HashSearchResult.class);
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

    private HttpEntity<String> entity(){
        HttpHeaders httpHeaders = new HttpHeaders();
        queryParameters.getHeaders().forEach((k,v) -> httpHeaders.add(k, v));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return entity;
    }
}
