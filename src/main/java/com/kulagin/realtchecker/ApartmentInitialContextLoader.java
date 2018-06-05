package com.kulagin.realtchecker;

import com.kulagin.realtchecker.model.Context;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ApartmentInitialContextLoader {
  private final FileUtil fileUtil;

  public ApartmentInitialContextLoader(FileUtil fileUtil) {
    this.fileUtil = fileUtil;
  }

  public Context loadContext(){
        final Context context = new Context();
    context.setDate(new Date());
    context.setLastJsonReportPath(fileUtil.getLastFilePath("json"));
    return context;
  }
}
