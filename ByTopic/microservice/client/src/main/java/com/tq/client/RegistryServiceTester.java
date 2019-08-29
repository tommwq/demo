package com.tq.client;

import com.tq.microservice.registryservice.RegisterInstanceRequest;
import com.tq.microservice.registryservice.RegisterInstanceResponse;
import com.tq.microservice.registryservice.QueryServiceRequest;
import com.tq.microservice.registryservice.QueryServiceResponse;
import com.tq.microservice.registryservice.RegistryServiceGrpc;
import com.tq.microservice.common.InstanceId;
import com.tq.microservice.common.InstanceLocation;
import com.tq.microservice.common.InstanceLocalId;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import java.util.logging.Logger;

public class RegistryServiceTester {

  private static final Logger logger = Logger.getLogger(ClientApplication.class.getName());
  private final RegistryServiceGrpc.RegistryServiceBlockingStub blockingStub;
  private final RegistryServiceGrpc.RegistryServiceStub stub;

  public RegistryServiceTester (ManagedChannel channel) {
    blockingStub = RegistryServiceGrpc.newBlockingStub(channel);
    stub = RegistryServiceGrpc.newStub(channel);
  }

  public void test() throws Exception {
    blockingStub.registerInstance(RegisterInstanceRequest.newBuilder()
                                  .setInstanceId(InstanceId.newBuilder()
                                                 .setLocation(InstanceLocation.newBuilder()
                                                              .setHost("127.0.0.1")
                                                              .setPort(4321)
                                                              .build())
                                                 .setLocalId(InstanceLocalId.newBuilder()
                                                             .setStartTime(1)
                                                             .setPid(1)
                                                             .build())
                                                 .build())
                                  .setServiceName("TestRgistry")
                                  .setServiceVersion("test")
                                  .build());

    QueryServiceResponse response = blockingStub.queryService(QueryServiceRequest.newBuilder()
                                                              .setServiceName("TestRgistry")
                                                              .setServiceVersion("test")
                                                              .build());
    logger.info(response.toString());
  }  
}
