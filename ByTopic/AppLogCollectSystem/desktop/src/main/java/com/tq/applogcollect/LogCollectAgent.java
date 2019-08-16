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
import java.util.List;
import static com.tq.applogcollect.Constant.DEFAULT_LOG_COUNT;
import static com.tq.applogcollect.Constant.INVALID_COUNT;
import static com.tq.applogcollect.Constant.INVALID_SEQUENCE;

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
          long sequence = command.getSequence();
          int count = command.getCount();

          if (sequence == INVALID_SEQUENCE) {
            sequence = Logger.instance().maxSequence();
          }

          if (count == INVALID_COUNT) {
            count = sequence < DEFAULT_LOG_COUNT ? (int) sequence : DEFAULT_LOG_COUNT;
          }

          reportLog(Logger.instance().queryLog(sequence, count));
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

  private void reportLog(List<LogRecord> logs) {
    logs.stream()
      .forEach(logRecord -> inputStream.onNext(logRecord));
  }
}
