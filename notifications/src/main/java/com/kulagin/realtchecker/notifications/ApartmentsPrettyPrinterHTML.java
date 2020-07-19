package com.kulagin.realtchecker.notifications;

import static org.springframework.util.StringUtils.isEmpty;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.kulagin.realtchecker.core.model.Apartment;
import com.kulagin.realtchecker.core.model.ApartmentPretty;
import com.kulagin.realtchecker.core.model.Context;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@AllArgsConstructor
public class ApartmentsPrettyPrinterHTML {
  private final Mustache.Compiler mustacheCompiler;

  public void makePrettyPrint(Context context) {
    List<Apartment> apartmentList = context.getApartments();
    log.info("Make a pretty print");
    List<ApartmentPretty> apartmentPretties = apartmentList.stream().map((apartment -> ApartmentPretty
        .builder()
        .address(isEmpty(apartment.getLocation().getAddress())? apartment.getLocation().getUserAddress() : apartment.getLocation().getAddress())
        .price(String.valueOf(apartment.getPrice().getAmount()))
        .lastFloor(apartment.getFloor() == apartment.getNumberOfFloors())
        .area(apartment.getArea())
        .url(apartment.getUrl()).build())).collect(Collectors.toList());
    List<ApartmentPretty> lastFloorOnly = apartmentPretties.stream().filter((apartmentPretty -> apartmentPretty.isLastFloor())).collect(Collectors.toList());

    Map<String, Object> scope = new HashMap<>();
    scope.put("all", apartmentPretties);
    scope.put("lastFloor", lastFloorOnly);

    try {
      Template mustacheTemplate = mustacheCompiler.compile(new InputStreamReader(new ClassPathResource("pretty_print.mustache").getInputStream(), "UTF-8"));
        var file = File.createTempFile("apartments", ".html");
        mustacheTemplate.execute(scope, new FileWriter(file));
      context.setPrettyPrintFile(file);
      context.setPrettyPrintFormat("HTML");
      context.setChangesPrettyPrint(printNotificationBody(context));
    } catch (IOException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
  }

  public String printNotificationBody(Context context) {
    StringWriter sw = null;
    try {
      Map<String, Object> scope = new HashMap<>();
      scope.put("r", context.getCompareApartmentResult());
      Template mustacheTemplate = mustacheCompiler.compile(new InputStreamReader(new ClassPathResource("email_body.mustache").getInputStream(), "UTF-8"));
      sw = new StringWriter();
      mustacheTemplate.execute(scope, sw);
    } catch (IOException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
    return sw.toString();
  }
}
