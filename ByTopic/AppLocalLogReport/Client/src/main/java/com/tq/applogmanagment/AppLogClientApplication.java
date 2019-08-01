package com.tq.applogmanagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class AppLogClientApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(AppLogClientApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
      AppLogClient client = new AppLogClient("172.24.20.112", 50051);
      try {
          client.report();
      } finally {
          client.shutdown();
      }
  }
}
