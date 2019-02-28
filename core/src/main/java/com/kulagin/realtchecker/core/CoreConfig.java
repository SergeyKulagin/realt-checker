package com.kulagin.realtchecker.core;

import com.samskivert.mustache.Mustache;
import org.springframework.boot.autoconfigure.mustache.MustacheEnvironmentCollector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CoreConfig {
  @Bean
  public Mustache.Compiler mustacheCompiler(
      Mustache.TemplateLoader templateLoader,
      Environment environment) {
    MustacheEnvironmentCollector collector = new MustacheEnvironmentCollector();
    collector.setEnvironment(environment);
    return Mustache
        .compiler()
        .defaultValue("")
        .withLoader(templateLoader)
        .withCollector(collector);
  }

  @Bean
  public TaskScheduler taskScheduler(){
    ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    taskScheduler.setPoolSize(1);
    return taskScheduler;
  }

  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }
}
