package com.tq.applogcollect.storage;

import com.tq.applogcollect.AppLogCollectProto.Log;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

public class Memory implements LogStorage {

  private static ConcurrentHashMap<String, ArrayList<Log>> table = new ConcurrentHashMap<>();
  
  @Override
  public void save(Log log) {
    // String deviceId = log.getDeviceId();
    // if (!table.containsKey(deviceId)) {
    //   table.put(deviceId, new ArrayList<>());
    // }

    String deviceId = "";
    table.get(deviceId).add(log);
  }
  
  @Override
  public List<Log> load(String deviceId, long sequence, int count) {
    // ignore sequence and count
    // if (!table.containsKey(deviceId)) {
    //   return new ArrayList<>();
    // }

    deviceId = "";
    return table.get(deviceId);
  }
}
