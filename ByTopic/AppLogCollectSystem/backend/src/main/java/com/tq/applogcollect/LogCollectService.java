package com.tq.applogcollect;

import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.AppLogCollectProto.LogQueryCommand;
import io.grpc.stub.StreamObserver;

public class LogCollectService extends LogCollectServiceGrpc.LogCollectServiceImplBase {
  @Override
  public StreamObserver<LogRecord> report(StreamObserver<LogQueryCommand> resultStream) {
    return new StreamObserver<LogRecord>() {
      @Override
      public void onNext(LogRecord report) {
      }

      @Override
      public void onError(Throwable throwable) {
        // TODO
      }

      @Override
      public void onCompleted() {
      }
    };
  }
}
