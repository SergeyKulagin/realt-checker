package com.kulagin.realtchecker.core.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "service.realtby")
@Getter
@Setter
public class RealtConfigurationProperties {
    private String searchHash;
    private Duration queryDelay;
    private Map<String, String> parameters;
    private Map<String, String> headers;
    private Map<String, String> urls;
}
