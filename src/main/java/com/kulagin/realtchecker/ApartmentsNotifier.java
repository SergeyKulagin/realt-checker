package com.kulagin.realtchecker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kulagin.realtchecker.model.Apartment;
import com.kulagin.realtchecker.model.Context;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Component
@Log4j2
public class ApartmentsNotifier {
  @Autowired
  private JavaMailSender emailSender;
  @Value("${spring.mail.to}")
  private String to;

  public void notify(Context context) {
    try {
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom("sergey.realt.program@no-spam.com");
      helper.setTo(to);
      helper.setSubject("Realt program update");
      helper.setText("test");
      FileSystemResource file = new FileSystemResource(new File(context.getHtmlReportPath()));
      helper.addAttachment("Report.html", file);
      emailSender.send(message);

    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  private List<Apartment> loadPrevious(Context context) {
    ObjectMapper objectMapper = new ObjectMapper();
    try (FileInputStream fis = new FileInputStream(context.getLastJsonReportPath().toFile())) {
      List<Apartment> apartments = objectMapper.readValue(fis, new TypeReference<List<Apartment>>() {
      });
      return apartments;
    } catch (IOException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
  }
}
