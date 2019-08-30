package com.tq.client;

import com.tq.microservice.common.Result;
import com.tq.microservice.configurationservice.PostConfigurationResponse;
import com.tq.microservice.configurationservice.PostConfigurationRequest;
import com.tq.microservice.configurationservice.QueryConfigurationResponse;
import com.tq.microservice.configurationservice.QueryConfigurationRequest;
import com.tq.microservice.configurationservice.ConfigurationServiceGrpc;
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

public class ConfigurationServiceTester {

  private static final Logger logger = Logger.getLogger(ClientApplication.class.getName());
  private final ConfigurationServiceGrpc.ConfigurationServiceBlockingStub blockingStub;

  public ConfigurationServiceTester (ManagedChannel channel) {
    blockingStub = ConfigurationServiceGrpc.newBlockingStub(channel);
  }

  public void test() throws Exception {
    blockingStub.postConfiguration(PostConfigurationRequest.newBuilder()
                                   .setServiceName("TestRgistry")
                                   .setServiceVersion("test")
                                   .setConfigurationVersion("t")
                                   .setConfigurationContent("abc")
                                   .build());

    QueryConfigurationResponse response = blockingStub.queryConfiguration(QueryConfigurationRequest.newBuilder()
                                                                          .setServiceName("TestRgistry")
                                                                          .setServiceVersion("test")
                                                                          .setConfigurationVersion("t")
                                                                          .build());
    logger.info(response.toString());
  }  
}
