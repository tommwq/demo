package com.tq.applogcollect.agent;

import com.tq.applogcollect.AppLogCollectProto.LogLevel;
import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.AppLogCollectProto.LogQueryCommand;
import com.tq.applogcollect.AppLogCollectProto.ModuleVersion;
import com.tq.applogcollect.LogCollectServiceGrpc;
import com.tq.applogcollect.Logger;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import java.util.List;
import static com.tq.applogcollect.Constant.DEFAULT_LOG_COUNT;
import static com.tq.applogcollect.Constant.INVALID_COUNT;
import static com.tq.applogcollect.Constant.INVALID_SEQUENCE;

/**
 * Receive LogQueryCommand, send requested logs.
 */
public class LogReporter implements StreamObserver<LogQueryCommand> {

  private LogAgent agent;
  
  public LogReporter(LogAgent aAgent) {
    agent = aAgent;
  }

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

    agent.reportLog(Logger.instance().queryLog(sequence, count));
  }
                
  @Override
  public void onError(Throwable error) {
    // 通知agent失败。
    // inputStream.onCompleted();
    // TODO 重写重连机制。
    // new Thread(() -> {
    //     try {
    //       Thread.sleep(10 * 1000);
    //       LogAgent.this.start();
    //     } catch (InterruptedException e) {
    //       // ignore
    //     }
    // }).start();
  }
                
  @Override
  public void onCompleted() {
    // TODO 通知agent。
    // inputStream.onCompleted();
    // inputStream = null;
    // System.err.println("complete");
  }
}

