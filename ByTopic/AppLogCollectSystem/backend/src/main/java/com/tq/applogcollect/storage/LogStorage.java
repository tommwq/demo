package com.tq.applogcollect.storage;

import com.tq.applogcollect.AppLogCollectProto.Log;
import java.util.List;

public interface LogStorage {
  void save(Log log);
  List<Log> load(String deviceId, long sequence, int count);
}
