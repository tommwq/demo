package com.tq.applogcollect;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class AppLogCollectApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(AppLogCollectApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
      final LogCollectServer server = new LogCollectServer();
      server.start();
      server.blockUntilShutdown();
      System.out.println("ok");
  }
}
