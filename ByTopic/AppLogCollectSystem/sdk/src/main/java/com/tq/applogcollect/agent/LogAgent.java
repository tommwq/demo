package com.tq.applogcollect.agent;

import com.tq.applogcollect.AppLogCollectProto.Command;
import com.tq.applogcollect.AppLogCollectProto.Log;
import com.tq.applogcollect.AppLogCollectProto.LogType;
import com.tq.applogcollect.AppLogCollectProto.ModuleInfo;

import com.tq.applogcollect.LogCollectServiceGrpc;
import com.tq.applogcollect.Logger;
import com.tq.applogcollect.SimpleLogger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import java.util.List;
import static com.tq.applogcollect.Constant.DEFAULT_LOG_COUNT;
import static com.tq.applogcollect.Constant.INVALID_COUNT;
import static com.tq.applogcollect.Constant.INVALID_SEQUENCE;

public class LogAgent implements Logger.LogSubscriber {

  private final ManagedChannel channel;
  private final LogCollectServiceGrpc.LogCollectServiceStub stub;
  private static final Logger logger = SimpleLogger.instance();
  private LogReportSession session;
  
  public LogAgent(String host, int port) {
    channel = ManagedChannelBuilder.forAddress(host, port)
      .usePlaintext()
      .build();

    stub = LogCollectServiceGrpc.newStub(channel);
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
    session.setLogOutputStream(stub.report(session));
    session.reportDeviceAndAppInfo();
    logger.setSubscriber(this);
  }
}
