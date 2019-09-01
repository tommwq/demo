package com.tq.applogmanagement;

import com.tq.applogmanagement.agent.LogAgent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class AppLogClientApplication implements CommandLineRunner {

  private static final Logger logger = SimpleLogger.instance();

  public static void main(String[] args) throws Exception {    
    SpringApplication.run(AppLogClientApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    DeviceAndAppConfig info = new DeviceAndAppConfig()
      .setAppVersion("0.1.0")
      .setModuleVersion("client", "0.1.0")
      .setDeviceId(UUID.randomUUID().toString());

    StorageConfig config = new StorageConfig()
      .setFileName("a.blk")
      .setBlockSize(4096)
      .setBlockCount(8);
    
    logger.open(config, info);
    
    // report device and app info on log report agent start.
    LogAgent agent = new LogAgent("localhost", 50051);
    agent.start();

    // record user defined message
    logger.log("hello", info, config);

    // record method and variables
    logger.trace();
    int x = 1;
    logger.print(x);
    
    // record exception
    Throwable error = new Throwable("Test");
    logger.error(error);

    // record user defined message
    logger.log("bye");

    Thread.sleep(10000);
    agent.shutdown();
    logger.close();
  }
}
