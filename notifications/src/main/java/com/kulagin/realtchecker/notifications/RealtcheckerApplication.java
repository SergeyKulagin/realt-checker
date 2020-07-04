package com.kulagin.realtchecker.notifications;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kulagin.realtchecker.core.*;
import com.kulagin.realtchecker.core.model.Apartment;
import com.kulagin.realtchecker.core.model.Context;
import com.kulagin.realtchecker.notifications.util.DefaultErrorHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ErrorHandler;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@Log4j2
@ComponentScan({
    "com.kulagin.realtchecker.core",
    "com.kulagin.realtchecker.notifications"

})
@RequiredArgsConstructor
public class RealtcheckerApplication implements CommandLineRunner{

  public static final int ADDITIONAL_CHECK_INTERVAL_MINUTES = 5;
  @Autowired
  private Environment environment;

  @Autowired
  private ApartmentInitialContextLoader contextLoader;
  @Autowired
  private ApartmentsLoader aparmentsLoader;
  @Autowired
  private ApartmentsSorter apartmentsSorter;
  @Autowired
  private ApartmentsStorer apartmentsStorer;
  @Autowired
  private ApartmentsPrettyPrinter apartmentsPrettyPrinter;
  @Autowired
  private ApartmentsNotifier apartmentsNotifier;
  @Autowired
  private ApartmentsComparer apartmentsComparer;
  @Autowired
  private TaskScheduler taskScheduler;

  public static void main(String[] args) {
    SpringApplication.run(RealtcheckerApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    // run on start-up
    log.info("Run the check job with the schedule {}", environment.getProperty("cron.check-schedule"));
    runCheck();
  }

  @Scheduled(cron = "${cron.check-schedule}")
  public void runPeriodicalCheck(){
    runCheck();
  }

  private void runCheck() {
    final Context context = contextLoader.loadContext();
    final List<Apartment> apartmentList = aparmentsLoader.load();
    if(apartmentList.isEmpty()){
      log.info("The apartments list is empty, run additional check in {} minuts", ADDITIONAL_CHECK_INTERVAL_MINUTES);
      taskScheduler.schedule(()-> runCheck(), Instant.now().plus(ADDITIONAL_CHECK_INTERVAL_MINUTES, ChronoUnit.MINUTES));
      return;
    }
    context.setApartments(apartmentList);
    apartmentsSorter.sort(context);
    apartmentsStorer.store(context);
    apartmentsPrettyPrinter.printReport(context);
    apartmentsComparer.compare(context);
    apartmentsNotifier.notify(context);
  }

  @Bean
  public ObjectMapper objectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    return objectMapper;
  }

  @Bean
  public ErrorHandler errorHandler(){
    return new DefaultErrorHandler();
  }
}
