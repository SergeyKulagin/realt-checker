package com.kulagin;

import com.kulagin.realtchecker.*;
import com.kulagin.realtchecker.model.Apartment;
import com.kulagin.realtchecker.model.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class RealtcheckerApplication implements CommandLineRunner {

  @Autowired
  ConfigurableApplicationContext springContext;

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

  public static void main(String[] args) {
    SpringApplication.run(RealtcheckerApplication.class, args);
  }

  @Override
  public void run(String... strings) throws Exception {
    final Context context = new Context();
    context.setDate(new Date());
    final List<Apartment> apartmentList = aparmentsLoader.load();
    context.setApartments(apartmentList);
    apartmentsSorter.sort(context);
    apartmentsStorer.store(context);
    apartmentsPrettyPrinter.print(context);
    apartmentsNotifier.notify(context);

    springContext.close();
  }
}
