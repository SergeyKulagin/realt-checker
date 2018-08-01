package com.kulagin.realtchecker.impl;

import com.kulagin.realtchecker.ApartmentsLoader;
import com.kulagin.realtchecker.model.Apartment;
import com.kulagin.realtchecker.model.Area;
import com.kulagin.realtchecker.model.Location;
import com.kulagin.realtchecker.model.Price;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("realt-by")
@Log4j2
public class ApartmentsLoaderRealtBy implements ApartmentsLoader {

  @Value("${checker.query.realtby.search_hash}")
  private String searchHash;

  @Value("${checker.query.url}")
  private String baseUrl;

  @Override
  public List<Apartment> load() {
    final List<Apartment> apartments = new ArrayList<>();
    int page = 0;
    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
      while (true) {
        final String url = UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .queryParam("search", UriUtils.encode(searchHash, Charset.forName("UTF-8")))
            .queryParam("page", page)
            .build()
            .toString();

        log.info("Request for page {}", url);
        HttpUriRequest request = RequestBuilder.get().setUri(url).build();
        HttpResponse response = httpClient.execute(request);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
          FileCopyUtils.copy(response.getEntity().getContent(), bos);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

        final Document htmlPage = Jsoup.parse(bos.toString("UTF-8"));
        Elements flatItems = htmlPage.select(".bd-table-item");
        if (page == 1 || flatItems.isEmpty()) {
          break;
        }
        for (Element flatItem : flatItems) {
          apartments.add(parseItem(flatItem));
        }
        page++;
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return apartments;
  }

  private Apartment parseItem(Element flatItem) {
    return Apartment.builder()
        .id(parseId(flatItem.selectFirst(".ad a").attr("href")))
        .area(parseArea(flatItem.selectFirst(".pl > span").text()))
        .price(
            Price.builder()
                .amount(parsePrice(flatItem.selectFirst(".cena > span").attr("data-840")))
                .currency("usd")
                .build()
        )
        .location(
            Location
                .builder()
                .address(flatItem.selectFirst(".ad a").text())
                .userAddress(flatItem.selectFirst(".ad a").text())
                .build()
        )
        .floor(parseFloor(flatItem.selectFirst(".ee > span").text()))
        .numberOfFloors(parseNumberOfFloors(flatItem.selectFirst(".ee > span").text()))
        .url(flatItem.selectFirst(".ad a").attr("href"))
        .build();
  }

  private Integer parseFloor(String fragment) {
    String[] fragments = fragment.split("/");

    String floorFragment = fragments.length > 0 ? fragments[0].replaceAll("[^0-9.]", ""): "0";
    return parseIntSilently(floorFragment);
  }

  private Integer parseNumberOfFloors(String fragment) {
    String[] fragments = fragment.split("/");
    String floorFragment = fragments.length > 1 ? fragments[1].replaceAll("[^0-9.]", "") : "0";
    return parseIntSilently(floorFragment);
  }

  private Integer parseId(String url) {
    int lastSlash = url.length() - 1;
    int prevLastSlash = url.substring(0, lastSlash).lastIndexOf("/");
    return parseIntSilently(url.substring(prevLastSlash + 1, lastSlash));
  }

  private Area parseArea(String area) {
    String[] areaParts = area.split("/");
    return Area
        .builder()
        .total(parseDoubleSilently(areaParts[0]))
        .living(parseDoubleSilently(areaParts[1]))
        .kitchen(parseDoubleSilently(areaParts[2]))
        .build();
  }

  private Double parsePrice(String price) {
    return Double.parseDouble(
        price
            .replaceAll(" ", "")
            .replaceAll("&nbsp;", "")
            .replaceAll("\\$", "")
    );
  }

  private Double parseDoubleSilently(String val) {
    try {
      return Double.parseDouble(val.replace(",", "."));
    } catch (NumberFormatException e) {
      return 0.0;
    }
  }

  private Integer parseIntSilently(String val) {
    try {
      return Integer.parseInt(val.replace(",", "."));
    } catch (NumberFormatException e) {
      return 0;
    }
  }
}
