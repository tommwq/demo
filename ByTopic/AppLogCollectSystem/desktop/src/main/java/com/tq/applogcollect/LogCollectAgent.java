package com.tq.applogcollect;

import com.tq.applogcollect.AppLogCollectProto.LogLevel;
import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.AppLogCollectProto.LogQueryCommand;
import com.tq.applogcollect.AppLogCollectProto.ModuleVersion;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LogCollectAgent {

  private final ManagedChannel channel;
  private final LogCollectServiceGrpc.LogCollectServiceStub stub;
  private StreamObserver<LogRecord> inputStream = null;
  
  public LogCollectAgent(String host, int port) {
    channel = ManagedChannelBuilder.forAddress(host, port)
      .usePlaintext()
      .build();

      stub = LogCollectServiceGrpc.newStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public void start() throws InterruptedException {
    
    inputStream = stub.report(new StreamObserver<LogQueryCommand>() {
        @Override
        public void onNext(LogQueryCommand command) {
          sendLogRecords();
        }
                
        @Override
        public void onError(Throwable error) {}
                
        @Override
        public void onCompleted() {
          inputStream.onCompleted();
          inputStream = null;
        }
      });

    inputStream.onNext(Logger.instance().newEmptyLogRecord());
  }

  private void sendLogRecords() {
    Logger.getLogBuffer()
      .stream()
      .forEach(logRecord -> inputStream.onNext(logRecord));
  }
}
