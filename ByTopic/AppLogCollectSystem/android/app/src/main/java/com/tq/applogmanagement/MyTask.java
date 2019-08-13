package com.tq.applogmanagement;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import com.tq.applogmanagement.LogCollectServiceProtos.LogLevel;
import com.tq.applogmanagement.LogCollectServiceProtos.LogReport;
import com.tq.applogmanagement.LogCollectServiceProtos.LogReportResult;
import com.tq.applogmanagement.LogCollectServiceProtos.MethodParameter;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;

public class MyTask extends AsyncTask<Void,Void,Void> {
    private final WeakReference<Activity> activity;
    private ManagedChannel channel;
    private final CountDownLatch latch = new CountDownLatch(2);

    public MyTask(Activity aActivity) {
        activity = new WeakReference<Activity>(aActivity);
    }

    @Override
    protected Void doInBackground(Void... ignored) {
        try {
            String host = "172.24.20.112";
            int port = 50051;
            channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();

            LogCollectServiceGrpc.LogCollectServiceStub stub = LogCollectServiceGrpc.newStub(channel);
            StreamObserver<LogReport> reportStream = stub.reportLog(new StreamObserver<LogReportResult>() {
                    @Override
                    public void onNext(LogReportResult result) {
                        Log.d("TEST", "receive");
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
                .setModule("FROM ANDROID")
                .build();
            reportStream.onNext(x);
            reportStream.onCompleted();
            Log.d("TEST", "send");
            Log.d("TEST", " " + channel.isTerminated() + " " + channel.isShutdown());
            latch.countDown();
            latch.await();
            Log.d("TEST", "finish");
        } catch (Exception e) {
            Log.e("TEST", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void ignored) {
    }
}


