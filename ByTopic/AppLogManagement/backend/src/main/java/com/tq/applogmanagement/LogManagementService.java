package com.tq.applogmanagement;

import org.springframework.stereotype.Component;
import com.tq.applogmanagement.AppLogManagementProto.Log;
import com.tq.applogmanagement.AppLogManagementProto.Command;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;

@Component
public class LogManagementService extends LogManagementServiceGrpc.LogManagementServiceImplBase {

  private ConcurrentHashMap<String, LogSession> deviceTable = new ConcurrentHashMap<>();

  public LogSession getLogSession(String deviceId) {
    return deviceTable.get(deviceId);
  }

  public Set<String> getOnlineDeviceIdSet() {
    return deviceTable.keySet();
  }
  
  @Override
  public StreamObserver<Log> reportLog(StreamObserver<Command> outputStream) {
    System.err.println("report");
    return new LogSession(outputStream, deviceTable);
  }
}
