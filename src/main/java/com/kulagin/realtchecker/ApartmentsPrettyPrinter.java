package com.kulagin.realtchecker;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.kulagin.realtchecker.model.Apartment;
import com.kulagin.realtchecker.model.ApartmentPretty;
import com.kulagin.realtchecker.model.Context;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

@Component
@Log4j2
public class ApartmentsPrettyPrinter {
  private final FileUtil fileUtil;

  public ApartmentsPrettyPrinter(FileUtil fileUtil) {
    this.fileUtil = fileUtil;
  }

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
    MustacheFactory mf = new DefaultMustacheFactory();
    Mustache mustache = mf.compile("pretty_print.mustache");
    try {
      final Path path = fileUtil.getFilePath(context.getDate(), "html");
      context.setHtmlReportPath(path.toString());
      mustache.execute(new FileWriter(path.toFile()), scope);
    } catch (IOException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
  }

  public String printNotificationBody(Context context) {
    Map<String, Object> scope = new HashMap<>();
    scope.put("r", context.getCompareApartmentResult());
    MustacheFactory mf = new DefaultMustacheFactory();
    Mustache mustache = mf.compile("email_body.mustache");
    StringWriter sw = new StringWriter();
    mustache.execute(sw, scope);
    return sw.toString();
  }
}
