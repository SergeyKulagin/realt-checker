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

  public void print(Context context) {
    List<Apartment> apartmentList = context.getApartments();
    log.info("Make a pretty print");
    List<ApartmentPretty> apartmentPretties = apartmentList.stream().map((apartment -> ApartmentPretty
        .builder()
        .address(isEmpty(apartment.getLocation().getAddress())? apartment.getLocation().getUserAddress() : apartment.getLocation().getAddress())
        .price(apartment.getPrice().amount)
        .lastFloor(apartment.getFloor() == apartment.getNumberOfFloors())
        .url(apartment.getUrl()).build())).collect(Collectors.toList());
    Map<String, Object> scope = new HashMap<>();
    scope.put("a", apartmentPretties);
    MustacheFactory mf = new DefaultMustacheFactory();
    Mustache mustache = mf.compile("pretty_print.mustache");
    try {
      mustache.execute(new FileWriter(fileUtil.getFilePath(context.getDate(), "html").toFile()), scope);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
