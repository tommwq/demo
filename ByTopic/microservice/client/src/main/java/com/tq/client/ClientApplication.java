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

  private ClientApplication(String host, int port) {
    channel = ManagedChannelBuilder.forAddress(host, port)
      .usePlaintext()
      .build();
    blockingStub = GreetServiceGrpc.newBlockingStub(channel);
    stub = GreetServiceGrpc.newStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public void greet1(String name) throws Exception {
    GreetReply response = blockingStub.greet1(GreetRequest.newBuilder()
                                              .setName(name)
                                              .build());
    logger.info(response.getMessage());
  }

  public void greet2(String name) throws Exception {
    StreamObserver<GreetRequest> inputStream = stub.greet2(new StreamObserver<GreetReply>() {
        @Override
        public void onNext(GreetReply reply) {
          logger.info(reply.getMessage());
        }

        @Override
        public void onError(Throwable error) {
          error.printStackTrace(System.err);
        }

        @Override
        public void onCompleted() {
        }
      });

    inputStream.onNext(GreetRequest.newBuilder()
                       .setName(name)
                       .build());
    inputStream.onCompleted();

    Thread.sleep(3000);
  }
  
  public void greet3(String name) throws Exception {
    StreamObserver<GreetRequest> inputStream = stub.greet3(new StreamObserver<GreetReply>() {
        @Override
        public void onNext(GreetReply reply) {
          logger.info(reply.getMessage());
        }

        @Override
        public void onError(Throwable error) {
          error.printStackTrace(System.err);
        }

        @Override
        public void onCompleted() {
        }
      });

    inputStream.onNext(GreetRequest.newBuilder()
                       .setName(name)
                       .build());
    inputStream.onCompleted();

    Thread.sleep(3000);
  }
  
  public void greet4(String name) throws Exception {
    Iterator<GreetReply> outputStream = blockingStub.greet4(GreetRequest.newBuilder()
                                                            .setName(name)
                                                            .build());
    while (outputStream.hasNext()) {
      logger.info(outputStream.next().getMessage());
    }
  }

  public static void main(String[] args) throws Exception {
    ClientApplication app = new ClientApplication("localhost", 50052);
    app.greet1("John");
    app.greet2("John");
    app.greet3("John");
    app.greet4("John");        

    app.shutdown();
  }  
}
