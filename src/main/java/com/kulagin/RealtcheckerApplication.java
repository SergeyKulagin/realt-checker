package com.kulagin;

import com.kulagin.realtchecker.*;
import com.kulagin.realtchecker.model.Apartment;
import com.kulagin.realtchecker.model.Context;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@SpringBootApplication
@EnableScheduling
@Log4j2
public class RealtcheckerApplication implements CommandLineRunner{

  @Autowired
  ConfigurableApplicationContext springContext;

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

  public static void main(String[] args) {
    SpringApplication.run(RealtcheckerApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    runCheck();
  }

  @Scheduled(cron = "${cron.check-schedule}")
  public void runPeriodicalCheck(){
    runCheck();
  }

  private void runCheck(){
    final Context context = contextLoader.loadContext();
    final List<Apartment> apartmentList = aparmentsLoader.load();
    context.setApartments(apartmentList);
    apartmentsSorter.sort(context);
    apartmentsStorer.store(context);
    apartmentsPrettyPrinter.printReport(context);
    apartmentsComparer.compare(context);
    apartmentsNotifier.notify(context);
  }
}
