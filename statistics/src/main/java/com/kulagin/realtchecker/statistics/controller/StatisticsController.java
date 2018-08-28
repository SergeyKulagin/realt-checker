package com.kulagin.realtchecker.statistics.controller;

import com.kulagin.realtchecker.statistics.model.MongoApartment;
import com.kulagin.realtchecker.statistics.repo.ApartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@AllArgsConstructor
public class StatisticsController {
  private final ApartmentRepository apartmentRepository;

  @RequestMapping(
      method = GET,
      path = "/search"
  )
  public String searchByAddress(@RequestParam(name = "term") String term, Model model) {
    final List<MongoApartment> apartments = apartmentRepository.searchByAddress(term);
    model.addAttribute("apartments", apartments);
    return "apartments";
  }
}
