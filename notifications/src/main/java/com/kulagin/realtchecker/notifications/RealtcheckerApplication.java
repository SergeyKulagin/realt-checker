package com.kulagin.realtchecker.notifications;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ErrorHandler;

import com.kulagin.realtchecker.notifications.bot.TriggerCheckerEvent;
import com.kulagin.realtchecker.notifications.util.DefaultErrorHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableScheduling
@Log4j2
@ComponentScan({
        "com.kulagin.realtchecker.core",
        "com.kulagin.realtchecker.notifications"
    
})
@RequiredArgsConstructor
public class RealtcheckerApplication implements CommandLineRunner {
    
    private final Environment environment;
    private final List<Checker> checkers;
    
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
    @EventListener(TriggerCheckerEvent.class)
    public void runPeriodicalCheck() {
        runCheck();
    }
    
    private void runCheck() {
        checkers.forEach(Checker::check);
    }
    
    @Bean
    public ErrorHandler errorHandler() {
        return new DefaultErrorHandler();
    }
}
