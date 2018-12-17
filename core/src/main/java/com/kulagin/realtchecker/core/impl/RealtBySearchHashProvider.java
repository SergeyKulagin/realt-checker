package com.kulagin.realtchecker.core.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class RealtBySearchHashProvider {
    private final RestTemplate restTemplate;

    public String get() {
        //1. get page to get the secret-hash
        ResponseEntity<String> page = restTemplate.getForEntity(
                "https://realt.by/sale/flats/search/" ,
                String.class
        );
        final Document pageWithHash = Jsoup.parse(page.getBody());
        final Element hashElement = pageWithHash.getElementById("secret-hash");
        final String hash = hashElement.attr("value");
        //2. get search hash
        //todo parameters
        ResponseEntity<HashSearchResult> hashSearchResultResponse = restTemplate.getForEntity("https://realt.by/", HashSearchResult.class);
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
