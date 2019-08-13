package com.tq.applogmanagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class AppLogClientApplication implements CommandLineRunner {

  @FunctionalInterface
  private interface Executor {
    void run();
  }
  
  private Logger logger = new Logger();

  public static void main(String[] args) {
    SpringApplication.run(AppLogClientApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    join("a","b","c");

    execute(() -> {
        long lsn = logger.enter();
        logger.leave(lsn);
      });
                
    // AppLogClient client = new AppLogClient("172.24.20.112", 50051);
    // try {
    //     client.report();
    // } finally {
    //     client.shutdown();
    // }
  }

  private void execute(Executor executor) {
    executor.run();
  }

  private String join(String... texts) {
    long lsn = logger.enter(texts);
    String result = String.join(",", texts);
    logger.leave(lsn, result);
    return result;
  }
}
