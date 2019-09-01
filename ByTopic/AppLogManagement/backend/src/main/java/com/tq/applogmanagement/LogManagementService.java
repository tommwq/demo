package com.tq.applogmanagement;

import com.tq.applogmanagement.AppLogManagementProto.Command;
import com.tq.applogmanagement.AppLogManagementProto.DeviceAndAppInfo;
import com.tq.applogmanagement.AppLogManagementProto.Empty;
import com.tq.applogmanagement.AppLogManagementProto.Log;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import org.springframework.stereotype.Component;

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

  @Override
  public void queryOnlineDevice(Empty input, StreamObserver<DeviceAndAppInfo> outputStream) {
  }

  @Override
  public void queryLog(Command input, StreamObserver<Log> outputStream) {
  }
}
