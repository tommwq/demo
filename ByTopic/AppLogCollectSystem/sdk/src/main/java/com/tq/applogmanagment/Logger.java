package com.tq.applogmanagement;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.tq.applogmanagement.AppLogManagementProto.Command;
import com.tq.applogmanagement.AppLogManagementProto.DeviceAndAppInfo;
import com.tq.applogmanagement.AppLogManagementProto.ExceptionInfo;
import com.tq.applogmanagement.AppLogManagementProto.Log;
import com.tq.applogmanagement.AppLogManagementProto.LogHeader;
import com.tq.applogmanagement.AppLogManagementProto.LogType;
import com.tq.applogmanagement.AppLogManagementProto.MethodAndObjectInfo;
import com.tq.applogmanagement.AppLogManagementProto.MethodInfo;
import com.tq.applogmanagement.AppLogManagementProto.ModuleInfo;
import com.tq.applogmanagement.AppLogManagementProto.ObjectInfo;
import com.tq.applogmanagement.AppLogManagementProto.UserDefinedMessage;
import com.tq.applogmanagement.storage.LogStorage;
import com.tq.applogmanagement.storage.SimpleBlockStorage;
import com.tq.applogmanagement.utility.StringUtil;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.LinkedTransferQueue;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Logger {

  public interface LogSubscriber {
    void onLog(Log log);
  }
  
  void setSubscriber(LogSubscriber aSubscriber);
  void open(StorageConfig aConfig, DeviceAndAppConfig aInfo) throws IOException;
  Log deviceAndAppInfoLog();
  void close();
  void trace();  
  void log(String message, Object... parameters);
  void print(Object... parameters);
  void error(Throwable error);
  List<Log> queryLog(long sequence, int count);
  long maxSequence();
}
