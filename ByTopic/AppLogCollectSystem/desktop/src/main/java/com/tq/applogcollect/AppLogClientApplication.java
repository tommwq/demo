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

  public static void main(String[] args) {
    String deviceId = UUID.randomUUID().toString();
    
    Logger.Config config = new Logger.Config();
    config.setAppVersion("0.1.0");
    config.setModuleVersion("client", "0.1.0");
    config.setDeviceId(deviceId);
    Logger.changeConfig(config);
    
    SpringApplication.run(AppLogClientApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    new LogCollectAgent("localhost", 50051).start();

    LoggerStorage storage = new LoggerStorage(new SimpleBlockStorage(new File("blk").toPath(), 2 * 1024 * 1024));
    storage.open();
    
    long lsn = Logger.instance().enter();
    storage.write(Logger.lastLog());
    Logger.instance().leave(lsn);
    storage.write(Logger.lastLog());

    long seq = storage.maxSequence();
    System.err.println(seq);
    
    storage.read(seq, (int) seq)
      .forEach(System.err::println);

    storage.close();

    // int count = 12;
    // while (count-- > 0) {
    //   process();               
    //   Thread.sleep(5 * 1000);
    // }
  }

  private void process() {
    long lsn = Logger.instance().enter();
    Logger.instance().leave(lsn);
  }
}
