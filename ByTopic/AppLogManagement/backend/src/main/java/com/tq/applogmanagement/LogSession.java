package com.tq.applogmanagement;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.tq.applogmanagement.AppLogManagementProto.Log;
import com.tq.applogmanagement.AppLogManagementProto.Command;
import com.tq.applogmanagement.AppLogManagementProto.DeviceAndAppInfo;
import com.tq.applogmanagement.storage.LogStorage;
import com.tq.applogmanagement.storage.Memory;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

public class LogSession implements StreamObserver<Log> {

  private String deviceId = "";
  private ConcurrentHashMap<String, LogSession> deviceTable;
  private StreamObserver<Command> outputStream;
  private LogStorage storage = new Memory();
    
  public LogSession(StreamObserver<Command> aOutputStream, ConcurrentHashMap<String,LogSession> aDeviceTable) {
    outputStream = aOutputStream;
    deviceTable = aDeviceTable;
  }
      
  @Override
  public void onNext(Log newLog) {
    System.err.println(newLog.getHeader().toString());
    
    Any body = newLog.getBody();
    if (!body.is(DeviceAndAppInfo.class)) {
      try {
        DeviceAndAppInfo info = body.unpack(DeviceAndAppInfo.class);
        deviceId = info.getDeviceId();
        if (!deviceTable.containsKey(deviceId)) {
          deviceTable.put(deviceId, this);
        }
      } catch (InvalidProtocolBufferException e) {
        // ignore
      }
    }

    storage.save(newLog);
  }
    
  @Override
  public void onError(Throwable throwable) {
    deviceTable.remove(deviceId);
  }
    
  @Override
  public void onCompleted() {
    deviceTable.remove(deviceId);
    outputStream.onCompleted();
  }

  public void command() {
    outputStream.onNext(Command.newBuilder()
                        .setSequence(0)
                        .setCount(0)
                        .build());
  }
}
  

