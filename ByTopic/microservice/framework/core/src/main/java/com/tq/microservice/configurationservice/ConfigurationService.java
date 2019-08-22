package com.tq.configurationservice;

import com.tq.microservice.common.Result;
import com.tq.microservice.configurationservice.PostConfigurationReply;
import com.tq.microservice.configurationservice.PostConfigurationRequest;
import com.tq.microservice.configurationservice.QueryConfigurationReply;
import com.tq.microservice.configurationservice.QueryConfigurationRequest;
import com.tq.microservice.configurationservice.ConfigurationServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class ConfigurationService extends ConfigurationServiceGrpc.ConfigurationServiceImplBase {

  @Override
  public void postConfiguration(PostConfigurationRequest input, StreamObserver<PostConfigurationReply> outputStream) {
    PostConfigurationReply reply = PostConfigurationReply.newBuilder()
      .setResult(Result.OK)
      .build();

    outputStream.onNext(reply);
    outputStream.onCompleted();
  }

  @Override
  public void queryConfiguration(QueryConfigurationRequest input, StreamObserver<QueryConfigurationReply> outputStream) {
    QueryConfigurationReply reply = QueryConfigurationReply.newBuilder()
      // TODO use configuration storage
      .setConfigurationContent("foo=bar\nxyz=123")
      .build();

    outputStream.onNext(reply);
    outputStream.onCompleted();
  }

  @Override
  public void subscribeConfiguration(QueryConfigurationRequest input, StreamObserver<QueryConfigurationReply> outputStream) {
    // TODO push newly configuration to subscriber.
    outputStream.onNext(QueryConfigurationReply.newBuilder()
                        .setConfigurationContent("test")
                        .build());
    outputStream.onCompleted();
  }
}
