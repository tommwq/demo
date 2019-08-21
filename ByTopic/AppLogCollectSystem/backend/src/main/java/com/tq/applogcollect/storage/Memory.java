package com.tq.applogcollect.storage;

import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

public class Memory implements LogStorage {

  private static ConcurrentHashMap<String, ArrayList<LogRecord>> table = new ConcurrentHashMap<>();
  
  @Override
  public void save(LogRecord log) {
    String deviceId = log.getDeviceId();
    if (!table.containsKey(deviceId)) {
      table.put(deviceId, new ArrayList<>());
    }

    table.get(deviceId).add(log);
  }
  
  @Override
  public List<LogRecord> load(String deviceId, long sequence, int count) {
    // ignore sequence and count
    if (!table.containsKey(deviceId)) {
      return new ArrayList<>();
    }

    return table.get(deviceId);
  }
}
