package com.tq.applogcollect;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class LogCollectSystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(LogCollectSystemApplication.class, args);
  }

}
