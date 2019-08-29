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

public class GreetServiceTester {

  private static final Logger logger = Logger.getLogger(ClientApplication.class.getName());
  private final GreetServiceGrpc.GreetServiceBlockingStub blockingStub;
  private final GreetServiceGrpc.GreetServiceStub stub;

  public GreetServiceTester (ManagedChannel channel) {
    blockingStub = GreetServiceGrpc.newBlockingStub(channel);
    stub = GreetServiceGrpc.newStub(channel);
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

  public void test() throws Exception {
    greet1("John");
    greet2("John");
    greet3("John");
    greet4("John");        
  }  
}
