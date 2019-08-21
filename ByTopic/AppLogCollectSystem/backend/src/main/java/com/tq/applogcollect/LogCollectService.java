package com.tq.applogcollect;

import org.springframework.stereotype.Component;
import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.AppLogCollectProto.LogQueryCommand;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;

@Component
public class LogCollectService extends LogCollectServiceGrpc.LogCollectServiceImplBase {

  private ConcurrentHashMap<String, LogSession> deviceTable = new ConcurrentHashMap<>();

  public LogSession getLogSession(String deviceId) {
    return deviceTable.get(deviceId);
  }

  public Set<String> getOnlineDeviceIdSet() {
    return deviceTable.keySet();
  }
  
  @Override
  public StreamObserver<LogRecord> report(StreamObserver<LogQueryCommand> outputStream) {
    return new LogSession(outputStream, deviceTable);
  }
}
