package com.tq.applogcollect;

import org.springframework.stereotype.Component;
import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.AppLogCollectProto.LogQueryCommand;
import com.tq.applogcollect.storage.LogStorage;
import com.tq.applogcollect.storage.Memory;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class LogSession implements StreamObserver<LogRecord> {

  private String deviceId = "";
  private ConcurrentHashMap<String, LogSession> deviceTable;
  private StreamObserver<LogQueryCommand> outputStream;
  private LogStorage storage = new Memory();
    
  public LogSession(StreamObserver<LogQueryCommand> aOutputStream, ConcurrentHashMap<String,LogSession> aDeviceTable) {
    outputStream = aOutputStream;
    deviceTable = aDeviceTable;
  }
      
  @Override
  public void onNext(LogRecord newLog) {
    deviceId = newLog.getDeviceId();

    if (!deviceTable.containsKey(deviceId)) {
      deviceTable.put(deviceId, this);
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
    outputStream.onNext(LogQueryCommand.newBuilder()
                        .setSequence(0)
                        .setCount(0)
                        .build());
  }
}
  

