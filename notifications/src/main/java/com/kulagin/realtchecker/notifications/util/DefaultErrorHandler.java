package com.kulagin.realtchecker.notifications.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ErrorHandler;

@Slf4j
public class DefaultErrorHandler implements ErrorHandler {
  @Override
  public void handleError(Throwable throwable) {
    log.error("Error.", throwable);
  }
}
