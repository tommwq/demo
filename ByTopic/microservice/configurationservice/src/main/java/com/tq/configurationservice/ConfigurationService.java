package com.tq.configurationservice;

import com.tq.configurationservice.QueryConfigurationRequest;
import com.tq.configurationservice.QueryConfigurationReply;
import com.tq.configurationservice.PostConfigurationRequest;
import com.tq.configurationservice.PostConfigurationReply;
import com.tq.constant.Result;
import com.tq.configurationservice.ConfigurationServiceGrpc;
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
      .setConfigurationContent("test")
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
