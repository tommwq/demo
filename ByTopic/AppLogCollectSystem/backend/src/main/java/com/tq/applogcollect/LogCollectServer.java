package com.tq.applogcollect;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;

public class LogCollectServer {
  private int port = 50051;
  private Server underlyingServer = null;

  public int port() {
    return port;
  }

  public void changePort(int aPort) {
    if (underlyingServer != null) {
      throw new RuntimeException("cannot change port when server is running");
    }

    port = aPort;
  }

  public void start() throws IOException {
    boolean success = false;
    try {
      Runtime.getRuntime().addShutdownHook(new Thread() {
          @Override
          public void run() {
            LogCollectServer.this.stop();
          }});
      
      underlyingServer = ServerBuilder.forPort(port)
        .addService(new LogCollectService())
          .build();
      
      underlyingServer.start();
      success = true;
    } finally {
      if (!success) {
        underlyingServer = null;
      }
    }
  }

  public void stop() {
    if (underlyingServer != null) {
      underlyingServer.shutdown();
      underlyingServer = null;
    }
  }

  public void blockUntilShutdown() throws InterruptedException {
    if (underlyingServer != null) {
      underlyingServer.awaitTermination();
    }
  }
}
