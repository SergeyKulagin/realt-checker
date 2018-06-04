package com.kulagin.realtchecker;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Log4j2
public class FileUtil {

  @Value("${checker.store.path}")
  private String basePath;


  public Path getFilePath(Date date, String ext) {
    final Path folder = getFolderName(date);
    ensureFolder(folder);
    Path fileToStore = folder.resolve(getFileName(date, ext));
    return fileToStore;
  }


  private Path ensureFolder(Path path) {
    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);
      } catch (IOException e) {
        log.error("Error while storing the attachments", e);
      }
    }
    return path;
  }

  private Path getFolderName(Date date) {
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    return Paths.get(basePath, df.format(date));
  }

  private Path getFileName(Date date, String ext) {
    DateFormat df = new SimpleDateFormat("HH.mm.ss");
    return Paths.get(df.format(date) + "." + ext);
  }
}
