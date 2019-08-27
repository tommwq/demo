package com.tq.applogcollect.agent;

import com.tq.applogcollect.AppLogCollectProto.Command;
import com.tq.applogcollect.AppLogCollectProto.Log;
import com.tq.applogcollect.AppLogCollectProto.LogType;
import com.tq.applogcollect.AppLogCollectProto.ModuleInfo;

import com.tq.applogcollect.LogCollectServiceGrpc;
import com.tq.applogcollect.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import java.util.List;
import static com.tq.applogcollect.Constant.DEFAULT_LOG_COUNT;
import static com.tq.applogcollect.Constant.INVALID_COUNT;
import static com.tq.applogcollect.Constant.INVALID_SEQUENCE;

public class LogAgent {

  private final ManagedChannel channel;
  private final LogCollectServiceGrpc.LogCollectServiceStub stub;
  private StreamObserver<Log> inputStream = null;
  
  public LogAgent(String host, int port) {
    channel = ManagedChannelBuilder.forAddress(host, port)
      .usePlaintext()
      .build();

    stub = LogCollectServiceGrpc.newStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public void start() throws InterruptedException {
    inputStream = stub.report(new LogReporter(this));
    // TODO report device and app info log
    // inputStream.onNext(Logger.instance().newEmptyLog());
  }

  protected void reportLog(List<Log> logs) {
    logs.stream()
      .forEach(logRecord -> inputStream.onNext(logRecord));
  }
}
