package com.tq.applogcollect;

import org.springframework.stereotype.Component;
import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.AppLogCollectProto.LogQueryCommand;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class LogCollectService extends LogCollectServiceGrpc.LogCollectServiceImplBase {


  public static HashMap<String, LogRecordInputStream> onlineDevices = new HashMap<>();

  public static class LogRecordInputStream implements StreamObserver<LogRecord> {
    private String deviceId = null;
    StreamObserver<LogQueryCommand> outputStream = null;
    public ArrayList<String> logBuffer = new ArrayList<>();
    Logger logger = LoggerFactory.getLogger(LogRecordInputStream.class);
    
    public LogRecordInputStream(StreamObserver<LogQueryCommand> aOutputStream) {
      outputStream = aOutputStream;
    }
      
    @Override
    public void onNext(LogRecord report) {
      deviceId = report.getDeviceId();
      System.err.println(deviceId);
      logger.warn("onNext");
      logger.warn(deviceId);
      if (!onlineDevices.containsKey(deviceId)) {
        onlineDevices.put(deviceId, this);
      }
      logBuffer.add(report.toString());
    }
    
    @Override
    public void onError(Throwable throwable) {
      onlineDevices.remove(deviceId);
    }

    @Override
    public void onCompleted() {
      onlineDevices.remove(deviceId);
      outputStream.onCompleted();
    }

    public void command() {
      outputStream.onNext(LogQueryCommand.newBuilder().build());
    }
  }
  
  @Override
  public StreamObserver<LogRecord> report(StreamObserver<LogQueryCommand> outputStream) {
    return new LogRecordInputStream(outputStream);
  }
}
