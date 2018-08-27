package com.kulagin.realtchecker.statistics;

import com.kulagin.realtchecker.core.ApartmentsLoader;
import com.kulagin.realtchecker.core.ApartmentsStorer;
import com.kulagin.realtchecker.core.model.Apartment;
import com.kulagin.realtchecker.core.model.Context;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
@EnableScheduling
@ComponentScan({
    "com.kulagin.realtchecker.core",
    "com.kulagin.realtchecker.statistics"
})
@Log4j2
public class StatisticsApplication implements CommandLineRunner{
  private final ApartmentsLoader apartmentsLoader;
  private final ApartmentsStorer apartmentsStorer;

  public static void main(String[] args) {
    SpringApplication.run(StatisticsApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    // run on start-up
    loadStatistics();
  }


  @Scheduled(cron = "${cron.check-schedule}")
  public void loadStatistics() {
    log.info("Loading apartments");
    final List<Apartment> apartmentList = apartmentsLoader.load();
    Context context = new Context();
    context.setApartments(apartmentList);
    apartmentsStorer.store(context);
  }
}
