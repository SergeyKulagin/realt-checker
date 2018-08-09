package com.kulagin.realtchecker.core;

import com.kulagin.realtchecker.core.model.Context;
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

@Component
@Log4j2
public class ApartmentsNotifier {
  @Autowired
  private JavaMailSender emailSender;
  @Autowired
  private ApartmentsPrettyPrinter printer;
  @Value("${spring.mail.to}")
  private String to;
  @Value("${checker.email.header.prefix}")
  private String emailPrefix;

  public void notify(Context context) {
    log.info("Notify about changes in the flats");
    try {
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom("sergey.realt.program@no-spam.com");
      helper.setTo(to);

      if (context.getCompareApartmentResult().hasChanges()) {
        helper.setSubject(emailPrefix + " - [REALT CHANGES]");
        log.info("Changes in the flats were detected => notify about them");
        helper.setText(printer.printNotificationBody(context));
      } else {
        helper.setSubject(emailPrefix + " - [REPORT]");
        log.info("No changes were detected => notify withing the report only");
        helper.setText("No changes were detected. Please check the report.");
      }
      FileSystemResource file = new FileSystemResource(new File(context.getHtmlReportPath()));
      helper.addAttachment("Report.html", file);
      emailSender.send(message);

    } catch (MessagingException e) {
      log.error("Notify error.", e);
    }
  }
}
