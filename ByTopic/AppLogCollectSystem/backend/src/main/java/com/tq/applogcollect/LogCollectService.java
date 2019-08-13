package com.tq.applogcollect;

import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.AppLogCollectProto.LogQueryCommand;
import io.grpc.stub.StreamObserver;

public class LogCollectService extends LogCollectServiceGrpc.LogCollectServiceImplBase {
  @Override
  public StreamObserver<LogRecord> report(StreamObserver<LogQueryCommand> outputStream) {
    return new StreamObserver<LogRecord>() {

      private boolean isFirst = true;
      
      @Override
      public void onNext(LogRecord report) {
        System.out.println("new request");
        System.out.println(report);

        if (isFirst) {
          isFirst = false;
          outputStream.onNext(LogQueryCommand.newBuilder().build());
        }
      }

      @Override
      public void onError(Throwable throwable) {
        System.out.println(throwable);
      }

      @Override
      public void onCompleted() {
          outputStream.onCompleted();
      }
    };
  }
}
