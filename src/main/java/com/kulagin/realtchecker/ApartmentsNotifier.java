package com.kulagin.realtchecker;

import com.kulagin.realtchecker.model.Context;
import com.sun.mail.smtp.SMTPTransport;
import com.sun.mail.util.BASE64EncoderStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
public class ApartmentsNotifier {
  /*@Autowired
  private JavaMailSender emailSender;*/
  @Value("${spring.mail.to}")
  private String to;

  public void notify(Context context) {

    Properties props = System.getProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "465");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.socketFactory.class",
        "javax.net.ssl.SSLSocketFactory");

    Session session = Session.getDefaultInstance(props, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("kulaginsp@gmail.com", "*****");
      }
    });
    session.setDebug(true);

    try {
      MimeMessage msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress("from@no-spam.com", "test"));
      msg.setRecipient(Message.RecipientType.TO, new InternetAddress("kulaginsp@gmail.com"));
      msg.setSubject("test");
      msg.setContent("test", "text/html");

      Transport.send(msg);
    } catch (MessagingException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }
}
