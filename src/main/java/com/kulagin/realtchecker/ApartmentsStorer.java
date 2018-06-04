package com.kulagin.realtchecker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kulagin.realtchecker.model.Apartment;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Log4j2
public class ApartmentsStorer {
  @Value("${checker.store.path}")
  private String basePath;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  {
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  public void store(List<Apartment> apartment) {
    try {
      final Path folder = getFolderName();
      ensureFolder(folder);
      Path fileToStore = folder.resolve(getFileName());
      objectMapper.writeValue(fileToStore.toFile(), apartment);
    } catch (IOException e) {
      //todo
    }
  }

  private Path ensureFolder(Path path){
    if(!Files.exists(path)){
      try {
        Files.createDirectories(path);
      } catch (IOException e) {
        //todo
      }
    }
    return path;
  }

  private Path getFolderName() {
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    return Paths.get(basePath, df.format(new Date()));
  }

  private Path getFileName() {
    DateFormat df = new SimpleDateFormat("HH.mm.ss");
    return Paths.get(df.format(new Date()) + ".json");
  }
}
