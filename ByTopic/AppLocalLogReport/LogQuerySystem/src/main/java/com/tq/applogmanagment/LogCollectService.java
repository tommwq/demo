package com.tq.applogmanagement;

import com.tq.applogmanagement.LogCollectServiceProtos.LogReport;
import com.tq.applogmanagement.LogCollectServiceProtos.LogReportResult;
import io.grpc.stub.StreamObserver;

public class LogCollectService extends LogCollectServiceGrpc.LogCollectServiceImplBase {
  @Override
  public StreamObserver<LogReport> reportLog(StreamObserver<LogReportResult> resultStream) {
    return new StreamObserver<LogReport>() {
      @Override
      public void onNext(LogReport report) {
        // TODO
      }

      @Override
      public void onError(Throwable throwable) {
        // TODO
      }

      @Override
      public void onCompleted() {
        resultStream.onCompleted();
      }
    };
  }
}
