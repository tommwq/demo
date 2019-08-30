package com.tq.greetservice;

import com.tq.microservice.registryservice.RegisterInstanceRequest;
import com.tq.microservice.registryservice.RegisterInstanceResponse;
import com.tq.microservice.registryservice.QueryServiceRequest;
import com.tq.microservice.registryservice.QueryServiceResponse;
import com.tq.microservice.registryservice.RegistryServiceGrpc;
import com.tq.microservice.common.InstanceId;
import com.tq.microservice.common.InstanceLocation;
import com.tq.microservice.common.InstanceLocalId;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
    int port = 51052;
    server = ServerBuilder.forPort(port)
      .addService(new GreetService())
      .build()
      .start();

    String host = hostAddress();
    register(host, port);

    // TODO 注册
    
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

  public String hostAddress() throws UnknownHostException {
    return InetAddress.getLocalHost().getHostAddress();
  }

  public void register(String host, int port) {
    Channel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
      .usePlaintext()
      .build();

    RegistryServiceGrpc.RegistryServiceBlockingStub blockingStub = RegistryServiceGrpc.newBlockingStub(channel);

    blockingStub.registerInstance(RegisterInstanceRequest.newBuilder()
                                  .setInstanceId(InstanceId.newBuilder()
                                                 .setLocation(InstanceLocation.newBuilder()
                                                              .setHost(host)
                                                              .setPort(port)
                                                              .build())
                                                 .setLocalId(InstanceLocalId.newBuilder()
                                                             .setStartTime(1)
                                                             .setPid(1)
                                                             .build())
                                                 .build())
                                  .setServiceName("com.tq.greetservice.GreetService")
                                  .setServiceVersion("0.1.0")
                                  .build());
  }
}
