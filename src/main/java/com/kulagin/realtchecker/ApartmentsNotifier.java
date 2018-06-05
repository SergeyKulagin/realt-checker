package com.kulagin.realtchecker;

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

@Component
@Log4j2
public class ApartmentsNotifier {
  @Autowired
  private JavaMailSender emailSender;
  @Autowired
  private ApartmentsPrettyPrinter printer;
  @Value("${spring.mail.to}")
  private String to;

  public void notify(Context context) {
    if (context.getCompareApartmentResult().hasChanges()) {
      log.info("Changes in the flats were detected => notify");
      try {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("sergey.realt.program@no-spam.com");
        helper.setTo(to);
        helper.setSubject("Realt program update");
        helper.setText(printer.printNotificationBody(context));
        FileSystemResource file = new FileSystemResource(new File(context.getHtmlReportPath()));
        helper.addAttachment("Report.html", file);
        emailSender.send(message);

      } catch (MessagingException e) {
        e.printStackTrace();
      }
    }
  }
}
