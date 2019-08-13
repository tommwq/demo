package com.tq.greetservice;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.util.logging.Logger;

public class GreetServiceApplication {

  private static final Logger logger = Logger.getLogger(GreetServiceApplication.class.getName());
  private Server server = null;
    
  public static void main(String[] args) throws Exception {
    GreetServiceApplication app = new GreetServiceApplication();
    app.start();
    app.blockUntilShutdown();
  }

  private void start() throws IOException {
    int port = 50052;
    server = ServerBuilder.forPort(port)
      .addService(new GreetService())
      .build()
      .start();
    
    Runtime.getRuntime().addShutdownHook(new Thread(() -> GreetServiceApplication.this.stop()));
  }

  private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }

  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }
}
