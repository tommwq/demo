package com.tq.applogcollect;

import org.springframework.stereotype.Component;
import com.tq.applogcollect.AppLogCollectProto.Log;
import com.tq.applogcollect.AppLogCollectProto.Command;
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
  public StreamObserver<Log> report(StreamObserver<Command> outputStream) {
    System.err.println("report");
    return new LogSession(outputStream, deviceTable);
  }
}
