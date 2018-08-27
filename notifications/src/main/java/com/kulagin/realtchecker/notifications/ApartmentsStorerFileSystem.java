package com.kulagin.realtchecker.notifications;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kulagin.realtchecker.core.ApartmentsStorer;
import com.kulagin.realtchecker.core.model.Apartment;
import com.kulagin.realtchecker.core.model.Context;
import com.kulagin.realtchecker.notifications.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Component
@Log4j2
@AllArgsConstructor
public class ApartmentsStorerFileSystem implements ApartmentsStorer {

  private final FileUtil fileUtil;
  private final ObjectMapper objectMapper;

  @Override
  public void store(Context context) {
    final List<Apartment> apartments = context.getApartments();
    log.info("Storing apartments.");
    try {
      objectMapper.writeValue(fileUtil.getFilePath(context.getDate(), "json").toFile(), apartments);
    } catch (IOException e) {
      log.error("Error while storing the apartments.", e);
    }
  }

  @Override
  public void loadPreviousApartments(Context context){
    Path lastFilePath = fileUtil.getLastFilePath("json");
     //no previous data exist, return empty list
    if(StringUtils.isEmpty(lastFilePath)){
      context.setPreviousApartments(Collections.emptyList());
    }
    ObjectMapper objectMapper = new ObjectMapper();
    try (FileInputStream fis = new FileInputStream(lastFilePath.toFile())) {
      List<Apartment> apartments = objectMapper.readValue(fis, new TypeReference<List<Apartment>>() {
      });
      context.setPreviousApartments(apartments);
    } catch (IOException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
  }
}
