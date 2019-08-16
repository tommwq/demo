package com.tq.applogcollect;

import java.io.File;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class AppLogClientApplication implements CommandLineRunner {

  public static void main(String[] args) throws Exception {
    String deviceId = UUID.randomUUID().toString();
    
    Logger.Config config = new Logger.Config();
    config.setAppVersion("0.1.0");
    config.setModuleVersion("client", "0.1.0");
    config.setDeviceId(deviceId);
    config.setFileName("a.blk");
    config.setBlockSize(4096);
    config.setBlockCount(8);
    Logger.instance().open(config);
    
    SpringApplication.run(AppLogClientApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    new LogCollectAgent("localhost", 50051).start();

    for (int i = 1; i < 100; i++) {
      long lsn = Logger.instance().enter();
      Logger.instance().leave(lsn);
    }

    // Logger.instance().close();
  }
}
