package com.tq.applogcollect;

import com.tq.applogcollect.agent.LogAgent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class AppLogClientApplication implements CommandLineRunner {

  public static void main(String[] args) throws Exception {
    String deviceId = UUID.randomUUID().toString();
    
    AppInfo info = new AppInfo();
    info.setAppVersion("0.1.0");
    info.setModuleVersion("client", "0.1.0");
    info.setDeviceId(deviceId);

    StorageConfig config = new StorageConfig();
    config.setFileName("a.blk");
    config.setBlockSize(4096);
    config.setBlockCount(8);
    
    Logger.instance().open(config, info);
    
    SpringApplication.run(AppLogClientApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    new LogAgent("localhost", 50051).start();

    for (int i = 1; i < 100; i++) {
      long lsn = Logger.instance().enter();
      Logger.instance().leave(lsn);
    }

    // Logger.instance().close();
  }
}
