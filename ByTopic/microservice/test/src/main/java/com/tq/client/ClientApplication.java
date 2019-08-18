package com.tq.client;

import com.tq.greetservice.GreetRequest;
import com.tq.greetservice.GreetReply;
import com.tq.greetservice.GreetServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import java.util.logging.Logger;

public class ClientApplication {

  private static final Logger logger = Logger.getLogger(ClientApplication.class.getName());
  private final ManagedChannel channel;
  private final GreetServiceGrpc.GreetServiceBlockingStub blockingStub;
  private final GreetServiceGrpc.GreetServiceStub stub;
  public Foo foo = new Foo();

  private ClientApplication(String host, int port) {
    channel = ManagedChannelBuilder.forTarget("abc://foo")
      .nameResolverFactory(foo)
      .usePlaintext()
      .build();
    blockingStub = GreetServiceGrpc.newBlockingStub(channel);
    stub = GreetServiceGrpc.newStub(channel);
  }

  public void foo() {
    try {
      GreetReply response = blockingStub.greet1(GreetRequest.newBuilder()
                                                .setName("ok")
                                                .build());
      logger.info(response.getMessage());
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }


  public static void main(String[] args) throws Exception {
    ClientApplication app = new ClientApplication("localhost", 50051);
    app.foo();
    app.foo();

    app.foo.bar.refresh();
    app.foo();

    app = new ClientApplication("localhost", 50051);
    app.foo();

    Thread.sleep(1000);
  }  
}
