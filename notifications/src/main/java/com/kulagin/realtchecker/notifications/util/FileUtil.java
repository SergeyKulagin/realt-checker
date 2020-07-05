package com.kulagin.realtchecker.notifications.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
@Log4j2
public class FileUtil {

  public static final String TIME_PATTERN = "HH.mm.ss";
  public static final String DATE_PATTERN = "dd.MM.yyyy";
  //@Value("${checker.store.path}")
  private String basePath;


  public Path getFilePath(Date date, String ext) {
    final Path folder = getFolderName(date);
    getLastFilePath("json");
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
    DateFormat df = new SimpleDateFormat(DATE_PATTERN);
    return Paths.get(basePath, df.format(date));
  }

  private Path getFileName(Date date, String ext) {
    DateFormat df = new SimpleDateFormat(TIME_PATTERN);
    return Paths.get(df.format(date) + "." + ext);
  }

  public Path getLastFilePath(String ext) {
    try {
      Optional<Path> lastFolderOptional = Files
          .list(Paths.get(basePath))
          .filter((file -> isParsableDate(DATE_PATTERN, file.toFile().getName())))
          .max(((f1, f2) -> f1.toFile().list().length > 0 ? Long.valueOf(f1.toFile().lastModified()).compareTo(f2.toFile().lastModified()) : -1));
      if (lastFolderOptional.isPresent()) {
        Path lastFolder = lastFolderOptional.get();
        Optional<Path> lastFile = Files
            .list(lastFolder)
            .filter((file -> file.toFile().getName().endsWith("." + ext)))
            .max((f1, f2) -> Long.valueOf(f1.toFile().lastModified()).compareTo(f2.toFile().lastModified()));
        if (lastFile.isPresent()) {
          return lastFile.get();
        }
      }
      return null;
    } catch (IOException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
  }

  public boolean isParsableDate(String format, String value) {
    try {
      new SimpleDateFormat(format).parse(value);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }
}
