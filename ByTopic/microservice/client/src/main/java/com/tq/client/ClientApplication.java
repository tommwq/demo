package com.tq.client;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class ClientApplication {

  private static final Logger logger = Logger.getLogger(ClientApplication.class.getName());

  private static ManagedChannel channel(String host, int port) {
    return ManagedChannelBuilder.forAddress(host, port)
      .usePlaintext()
      .build();
  }

  private static ManagedChannel channel(int port) {
    return channel("localhost", port);
  }

  public static void main(String[] args) throws Exception {
    ManagedChannel chan = channel(50053);
    // new GreetServiceTester(chan).test();
    // new RegistryServiceTester(chan).test();
    new ConfigurationServiceTester(chan).test();
    
    chan.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }  
}
