package com.tq.client;

import com.tq.configurationservice.QueryConfigurationRequest;
import com.tq.configurationservice.QueryConfigurationReply;
import com.tq.configurationservice.PostConfigurationRequest;
import com.tq.configurationservice.PostConfigurationReply;
import com.tq.configurationservice.ConfigurationServiceGrpc;
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
  private final ConfigurationServiceGrpc.ConfigurationServiceBlockingStub blockingStub;
  private final ConfigurationServiceGrpc.ConfigurationServiceStub stub;

  private ClientApplication(String host, int port) {
    channel = ManagedChannelBuilder.forAddress(host, port)
      .usePlaintext()
      .build();
    blockingStub = ConfigurationServiceGrpc.newBlockingStub(channel);
    stub = ConfigurationServiceGrpc.newStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public void test() throws Exception {
    
    System.out.println(blockingStub.postConfiguration(PostConfigurationRequest.newBuilder()
                                                      .setServiceName("com.tq.foo")
                                                      .setServiceVersion("1.0")
                                                      .setConfigurationVersion("")
                                                      .setConfigurationContent("XYZ")
                                                      .build())
                       .getResult()
                       .toString());

    System.out.println(blockingStub.queryConfiguration(QueryConfigurationRequest.newBuilder()
                                                       .setServiceName("com.tq.foo")
                                                       .setServiceVersion("1.0")
                                                       .setConfigurationVersion("")
                                                       .build())
                       .getConfigurationContent());
  }

  public static void main(String[] args) throws Exception {
    ClientApplication app = new ClientApplication("localhost", 12345);
    app.test();
    app.shutdown();
  }  
}
