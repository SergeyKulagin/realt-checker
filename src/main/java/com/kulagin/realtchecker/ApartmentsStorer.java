package com.kulagin.realtchecker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kulagin.realtchecker.model.Apartment;
import com.kulagin.realtchecker.model.Context;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Log4j2
public class ApartmentsStorer {


  private static final ObjectMapper objectMapper = new ObjectMapper();

  {
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  private final FileUtil fileUtil;

  public ApartmentsStorer(FileUtil fileUtil) {
    this.fileUtil = fileUtil;
  }

  public void store(Context context) {
    final List<Apartment> apartments = context.getApartments();
    log.info("Storing apartments");
    try {

      objectMapper.writeValue(fileUtil.getFilePath(context.getDate(), "json").toFile(), apartments);
    } catch (IOException e) {
      log.error("Error while storing the attachments", e);
    }
  }


}
