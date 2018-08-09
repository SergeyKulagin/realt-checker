package com.kulagin.realtchecker.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "checker.query")
@Getter
@Setter
@ToString
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
  private String pageParamName;
  private String priceMinParamName;
  private String priceMaxParamName;
  private String currencyParamName;
}
