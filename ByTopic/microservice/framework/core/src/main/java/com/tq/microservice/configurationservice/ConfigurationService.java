package com.tq.microservice.configurationservice;

import com.tq.microservice.common.Result;
import com.tq.microservice.configurationservice.adapter.ConfigurationServiceAdapter;
import com.tq.microservice.configurationservice.adapter.InMemoryConfigurationServiceAdapter;
import com.tq.microservice.configurationservice.PostConfigurationResponse;
import com.tq.microservice.configurationservice.PostConfigurationRequest;
import com.tq.microservice.configurationservice.QueryConfigurationResponse;
import com.tq.microservice.configurationservice.QueryConfigurationRequest;
import com.tq.microservice.configurationservice.ConfigurationServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class ConfigurationService extends ConfigurationServiceGrpc.ConfigurationServiceImplBase {

  // TODO add adapter table like RegistryService
  private ConfigurationServiceAdapter adapter = new InMemoryConfigurationServiceAdapter();

  @Override
  public void postConfiguration(PostConfigurationRequest input, StreamObserver<PostConfigurationResponse> outputStream) {

    String serviceName = input.getServiceName();
    String serviceVersion = input.getServiceVersion();
    String configurationVersion = input.getConfigurationVersion();
    String configurationContent = input.getConfigurationContent();

    adapter.createConfiguration(serviceName,
                                serviceVersion,
                                configurationVersion,
                                configurationContent);
    
    PostConfigurationResponse response = PostConfigurationResponse.newBuilder()
      .setResult(Result.OK)
      .build();

    outputStream.onNext(response);
    outputStream.onCompleted();
  }

  @Override
  public void queryConfiguration(QueryConfigurationRequest input, StreamObserver<QueryConfigurationResponse> outputStream) {

    String serviceName = input.getServiceName();
    String serviceVersion = input.getServiceVersion();
    String configurationVersion = input.getConfigurationVersion();

    String content = adapter.readConfiguration(serviceName,
                                               serviceVersion,
                                               configurationVersion);

    QueryConfigurationResponse response = QueryConfigurationResponse.newBuilder()
      .setConfigurationContent(content)
      .build();

    outputStream.onNext(response);
    outputStream.onCompleted();
  }

  @Override
  public void subscribeConfiguration(QueryConfigurationRequest input, StreamObserver<QueryConfigurationResponse> outputStream) {
    // TODO push newly configuration to subscriber.
    outputStream.onNext(QueryConfigurationResponse.newBuilder()
                        .setConfigurationContent("")
                        .build());
    outputStream.onCompleted();
  }
}
