package com.tq.applogmanagement.storage;

import com.tq.applogmanagement.AppLogManagementProto.Log;
import java.util.List;

public interface LogStorage {
  void save(Log log);
  List<Log> load(String deviceId, long sequence, int count);
}
