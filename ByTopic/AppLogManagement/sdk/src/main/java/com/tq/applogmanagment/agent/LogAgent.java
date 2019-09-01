package com.tq.applogmanagement.agent;

import com.tq.applogmanagement.AppLogManagementProto.Command;
import com.tq.applogmanagement.AppLogManagementProto.Log;
import com.tq.applogmanagement.AppLogManagementProto.LogType;
import com.tq.applogmanagement.AppLogManagementProto.ModuleInfo;

import com.tq.applogmanagement.LogManagementServiceGrpc;
import com.tq.applogmanagement.Logger;
import com.tq.applogmanagement.SimpleLogger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import java.util.List;
import static com.tq.applogmanagement.Constant.DEFAULT_LOG_COUNT;
import static com.tq.applogmanagement.Constant.INVALID_COUNT;
import static com.tq.applogmanagement.Constant.INVALID_SEQUENCE;

public class LogAgent implements Logger.LogSubscriber {

  private final ManagedChannel channel;
  private final LogManagementServiceGrpc.LogManagementServiceStub stub;
  private static final Logger logger = SimpleLogger.instance();
  private LogReportSession session;
  
  public LogAgent(String host, int port) {
    channel = ManagedChannelBuilder.forAddress(host, port)
      .usePlaintext()
      .build();

    stub = LogManagementServiceGrpc.newStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(30, TimeUnit.SECONDS);
  }

  @Override
  public void onLog(Log log) {
    session.report(log);
    // TODO
    System.err.println("report log " + log.getHeader().toString());
  }

  public void start() throws InterruptedException {
    session = new LogReportSession(this);
    session.setLogOutputStream(stub.reportLog(session));
    session.reportDeviceAndAppInfo();
    logger.setSubscriber(this);
  }
}
