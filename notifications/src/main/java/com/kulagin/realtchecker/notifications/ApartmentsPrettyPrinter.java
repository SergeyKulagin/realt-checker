package com.kulagin.realtchecker.notifications;

import com.kulagin.realtchecker.core.model.Apartment;
import com.kulagin.realtchecker.core.model.ApartmentPretty;
import com.kulagin.realtchecker.core.model.Context;
import com.kulagin.realtchecker.notifications.util.FileUtil;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

@Component
@Log4j2
@AllArgsConstructor
public class ApartmentsPrettyPrinter {
  private final FileUtil fileUtil;
  private final Mustache.Compiler mustacheCompiler;

  public void printReport(Context context) {
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
      final Path path = fileUtil.getFilePath(context.getDate(), "html");
      context.setHtmlReportPath(path.toString());
      mustacheTemplate.execute(scope, new FileWriter(path.toFile()));
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
