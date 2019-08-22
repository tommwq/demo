package com.tq.applogcollect.agent;

import com.tq.applogcollect.AppLogCollectProto.LogLevel;
import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.AppLogCollectProto.LogQueryCommand;
import com.tq.applogcollect.AppLogCollectProto.ModuleVersion;
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
  private StreamObserver<LogRecord> inputStream = null;
  
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
    inputStream.onNext(Logger.instance().newEmptyLogRecord());
  }

  protected void reportLog(List<LogRecord> logs) {
    logs.stream()
      .forEach(logRecord -> inputStream.onNext(logRecord));
  }
}
