package com.tq.applogcollect;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.DisposableBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class LogCollectServer {
  private int port = 50051;
  private Server underlyingServer = null;
  public LogCollectService service;

  public int port() {
    return port;
  }

  public LogCollectServer() throws Exception {
    service = new LogCollectService();
    underlyingServer = ServerBuilder.forPort(port)
      .addService(service)
      .build();

    underlyingServer.start();
  }

  public void destroy() {
    if (underlyingServer != null) {
      underlyingServer.shutdown();
    }
  }
}
