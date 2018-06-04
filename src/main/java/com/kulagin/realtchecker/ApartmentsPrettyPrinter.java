package com.kulagin.realtchecker;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.kulagin.realtchecker.model.Apartment;
import com.kulagin.realtchecker.model.ApartmentPretty;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class ApartmentsPrettyPrinter {
  public void print(List<Apartment> apartmentList, Date date) {
    List<ApartmentPretty> apartmentPretties = apartmentList.stream().map((apartment -> ApartmentPretty
        .builder()
        .address(isEmpty(apartment.getLocation().getAddress())? apartment.getLocation().getUserAddress() : apartment.getLocation().getAddress())
        .price(apartment.getPrice().amount)
        .url(apartment.getUrl()).build())).collect(Collectors.toList());
    Map<String, Object> scope = new HashMap<>();
    scope.put("a", apartmentPretties);
    MustacheFactory mf = new DefaultMustacheFactory();
    Mustache mustache = mf.compile("pretty_print.mustache");
    try {
      mustache.execute(new FileWriter("./test.html"), scope);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
