package com.tq.applogcollect.storage;

import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import java.util.List;

public interface LogStorage {
  void save(LogRecord log);
  List<LogRecord> load(String deviceId, long sequence, int count);
}
