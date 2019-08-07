package com.tq.test;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;
import com.tq.test.helloworld.HelloRequest;
import com.tq.test.helloworld.HelloReply;
import com.tq.test.helloworld.GreeterGrpc;

import javax.annotation.Nullable;

public class App {

  private static final Logger logger = Logger.getLogger(App.class.getName());
  private Server server;
    
  public static void main(String[] args) {
    try {
      final App app = new App();
      app.start();
      app.blockUntilShutdown();
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
  }

  private void start() throws IOException {
    int port = 50051;
    ServerBuilder serverBuilder = ServerBuilder.forPort(port)
      .addService(new GreeterImpl());

    serverBuilder.intercept(new ServerInterceptor() {
        @Override
        public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
          // logger.info(call.getAttributes().toString());
          // logger.info(next.toString());
          // logger.info(headers.toString());
          logger.info("intercept");
          return next.startCall(call, headers);
        }
      });

    serverBuilder.fallbackHandlerRegistry(new MyHandlerRegistry());

    server = serverBuilder.build().start();
    logger.info("started");
    Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          App.this.stop();
          System.err.println("shutdown");
        }
      });
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

  static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello1(HelloRequest req, StreamObserver<HelloReply> observer) {
      HelloReply reply = HelloReply.newBuilder()
        .setMessage("Hello " + req.getName())
        .build();

      observer.onNext(reply);
      observer.onCompleted();
    }
  }
}
