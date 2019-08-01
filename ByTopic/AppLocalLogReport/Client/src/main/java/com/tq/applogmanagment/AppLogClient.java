package com.tq.applogmanagement;

import com.tq.applogmanagement.LogCollectServiceProtos.LogLevel;
import com.tq.applogmanagement.LogCollectServiceProtos.LogRecord;
import com.tq.applogmanagement.LogCollectServiceProtos.LogRecordArray;
import com.tq.applogmanagement.LogCollectServiceProtos.LogQueryCommand;
import com.tq.applogmanagement.LogCollectServiceProtos.ModuleVersion;
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
        StreamObserver<LogRecordArray> recordStream = stub.report(new StreamObserver<LogQueryCommand>() {
                @Override
                public void onNext(LogQueryCommand command) {
                }
                
                @Override
                public void onError(Throwable error) {
                }
                
                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            });

        LogRecordArray x = LogRecordArray.newBuilder().build();
        recordStream.onNext(x);
        recordStream.onCompleted();
        latch.countDown();
        latch.await();
    }
}
