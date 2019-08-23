package com.tq.configurationservice;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
public class ConfigurationServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConfigurationServiceApplication.class, args);
	}

  @Override
  public void run(String... args) throws Exception {

    // TODO use configuration
    Server server = ServerBuilder.forPort(12345)
      .addService(new ConfigurationService())
      .build()
      .start();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> server.shutdown()));
    server.awaitTermination();
  }
}
