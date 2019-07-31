package com.tq.applogmanagment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class AppLogManagementApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(AppLogManagementApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("ok");
  }
}
