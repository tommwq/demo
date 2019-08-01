package com.tq.applogmanagement;

import com.tq.applogmanagement.LogCollectServiceProtos.LogLevel;
import com.tq.applogmanagement.LogCollectServiceProtos.LogReport;
import com.tq.applogmanagement.LogCollectServiceProtos.LogReportResult;
import com.tq.applogmanagement.LogCollectServiceProtos.MethodParameter;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AppLogClient {

    private final ManagedChannel channel;
    private final LogCollectServiceGrpc.LogCollectServiceStub stub;
    private final CountDownLatch latch = new CountDownLatch(2);

    public AppLogClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
            .usePlaintext()
            .build();
        stub = LogCollectServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void report() throws InterruptedException {
        StreamObserver<LogReport> reportStream = stub.reportLog(new StreamObserver<LogReportResult>() {
                @Override
                public void onNext(LogReportResult result) {
                }
                
                @Override
                public void onError(Throwable error) {
                }
                
                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            });

        LogReport x = LogReport.newBuilder()
            .setHeartbeat(true)
            .build();
        reportStream.onNext(x);
        reportStream.onCompleted();
        latch.countDown();
        latch.await();
    }
}
