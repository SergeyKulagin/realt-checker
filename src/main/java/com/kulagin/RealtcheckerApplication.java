package com.kulagin;

import com.kulagin.realtchecker.ApartmentsLoader;
import com.kulagin.realtchecker.model.Apartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class RealtcheckerApplication implements CommandLineRunner {

  @Autowired
  private ApartmentsLoader aparmentsLoader;

  public static void main(String[] args) {
    SpringApplication.run(RealtcheckerApplication.class, args);
  }

  @Override
  public void run(String... strings) throws Exception {
    List<Apartment> apartmentList = aparmentsLoader.load();
  }
}
