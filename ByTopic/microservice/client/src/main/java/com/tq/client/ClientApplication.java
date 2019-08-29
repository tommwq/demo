package com.tq.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import java.util.logging.Logger;

public class ClientApplication {

  private static final Logger logger = Logger.getLogger(ClientApplication.class.getName());

  private static ManagedChannel createChannel(String host, int port) {
    return ManagedChannelBuilder.forAddress(host, port)
      .usePlaintext()
      .build();
  }

  public static void main(String[] args) throws Exception {
    ManagedChannel channel = createChannel("localhost", 50052);

    // new GreetServiceTester(channel).test();
    new RegistryServiceTester(channel).test();
    
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }  
}
