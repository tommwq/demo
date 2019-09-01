package com.tq.applogmanagement;

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
public class LogManagementServer {
  private int port = 50051;
  private Server underlyingServer = null;
  private LogManagementService service;

  public LogManagementService getService() {
    return service;
  }

  public int port() {
    return port;
  }

  public LogManagementServer() throws Exception {
    service = new LogManagementService();
    underlyingServer = ServerBuilder.forPort(port)
      .addService(service)
      .build();

    System.err.println("**** started ****");

    underlyingServer.start();
  }

  public void destroy() {
    if (underlyingServer != null) {
      underlyingServer.shutdown();
    }
  }
}
