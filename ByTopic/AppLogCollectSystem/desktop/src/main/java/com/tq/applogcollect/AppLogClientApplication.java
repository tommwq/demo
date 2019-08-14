package com.tq.applogcollect;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class AppLogClientApplication implements CommandLineRunner {

  public static void main(String[] args) {
    Logger.Config config = new Logger.Config();
    config.setAppVersion("0.1.0");
    config.setModuleVersion("client", "0.1.0");
    config.setDeviceId("test_client");
    Logger.changeConfig(config);
    
    SpringApplication.run(AppLogClientApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    new LogCollectAgent("localhost", 50051).start();
    
    while (true) {
      process();               
      Thread.sleep(5 * 1000);
    }
  }

  private void process() {
    long lsn = Logger.instance().enter();
    Logger.instance().leave(lsn);
  }
}
