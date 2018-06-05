package com.kulagin.realtchecker;

import com.kulagin.realtchecker.model.Context;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class ApartmentInitialContextLoader {
  private final FileUtil fileUtil;

  public ApartmentInitialContextLoader(FileUtil fileUtil) {
    this.fileUtil = fileUtil;
  }

  public Context loadContext() {
    final Context context = new Context();
    context.setDate(new Date());
    context.setLastJsonReportPath(fileUtil.getLastFilePath("json"));
    log.info("Loading context {}", context);
    return context;
  }
}
