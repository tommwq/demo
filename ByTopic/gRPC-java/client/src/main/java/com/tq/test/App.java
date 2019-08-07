package com.tq.test;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import com.tq.test.helloworld.HelloRequest;
import com.tq.test.helloworld.HelloReply;
import com.tq.test.helloworld.GreeterGrpc;
import com.tq.test.helloworld.ByeRequest;
import com.tq.test.helloworld.ByeReply;
import com.tq.test.helloworld.FarewellerGrpc;

public class App {

  private static final Logger logger = Logger.getLogger(App.class.getName());
  private final ManagedChannel channel;
  private final GreeterGrpc.GreeterBlockingStub blockingStub;
  private final FarewellerGrpc.FarewellerBlockingStub blockingStub2;

  public App(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port)
         .usePlaintext()
         .build());
  }

  private App(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = GreeterGrpc.newBlockingStub(channel);
    blockingStub2 = FarewellerGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public void greet(String name) {
    HelloRequest request = HelloRequest.newBuilder()
      .setName(name)
      .build();

    try {
      HelloReply response = blockingStub.sayHello1(request);
      logger.info(response.getMessage());
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
  }

  public void bye(String name) {
    ByeRequest request = ByeRequest.newBuilder()
      .setName(name)
      .build();

    try {
      ByeReply response = blockingStub2.sayGoodBye(request);
      logger.info(response.getMessage());
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
  }
        
  public static void main(String[] args) {
    final App app = new App("localhost", 50051);
    try {
      app.greet("world");
      // app.bye("world");
      app.shutdown();
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
  }  
}
