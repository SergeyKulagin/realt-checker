package com.kulagin.realtchecker.core.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "checker.query")
@Getter
@Setter
public class QueryParameters {
    private Map<String, String> parameters;
    private Map<String, String> headers;
    private Map<String, String> urls;
}
