package com.kulagin.realtchecker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "checker.query")
@Getter
@Setter
public class CheckerQueryConfiguration {
  private String boundsLbLatParamName;
  private String boundsLbLongParamName;
  private String boundsRtLatParamName;
  private String boundsRtLongParamName;
  private double boundsLbLat;
  private double boundsLbLong;
  private double boundsRtLat;
  private double boundsRtLong;
  private int priceMin;
  private int priceMax;
  private String currency;
  private String walling;
  private String wallingParamName;
  private String url;
}
