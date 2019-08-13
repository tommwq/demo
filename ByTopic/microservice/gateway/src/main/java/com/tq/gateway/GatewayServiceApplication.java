package com.tq.gateway;

import com.tq.gateway.service.builder.ProxyServiceBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
public class GatewayServiceApplication implements CommandLineRunner {

  @Autowired
  GatewayConfig gatewayConfig;
  
  public static void main(String[] args) {
    SpringApplication.run(GatewayServiceApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    Server server = ServerBuilder.forPort(gatewayConfig.getPort())
      .fallbackHandlerRegistry(new UnregisteredServiceRegistry(new ProxyServiceBuilder().build(gatewayConfig)))
      .build()
      .start();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> server.shutdown()));
    server.awaitTermination();
  }
}
