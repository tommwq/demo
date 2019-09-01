package com.tq.applogmanagement.api;

import com.tq.applogmanagement.AppLogManagementProto.Empty;
import com.tq.applogmanagement.AppLogManagementProto.DeviceAndAppInfo;
import com.tq.applogmanagement.AppLogManagementProto.Log;
import java.util.List;

public interface DeviceLogManagementService {
  void open();
  void close();
  void onDeviceConnected(DeviceAndAppInfo info);
  void onDeviceDisconnected(String deviceId);
  List<String> onlineDeviceIdList();
  List<Log> readLogCache(String deviceId);
  void appendLogCache(String deviceId, List<Log> logList);
  void appendLogCache(String deviceId, Log log);
}
