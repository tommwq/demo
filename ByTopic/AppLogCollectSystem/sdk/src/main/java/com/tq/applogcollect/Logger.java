package com.tq.applogcollect;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.tq.applogcollect.AppLogCollectProto.Command;
import com.tq.applogcollect.AppLogCollectProto.DeviceAndAppInfo;
import com.tq.applogcollect.AppLogCollectProto.ExceptionInfo;
import com.tq.applogcollect.AppLogCollectProto.Log;
import com.tq.applogcollect.AppLogCollectProto.LogHeader;
import com.tq.applogcollect.AppLogCollectProto.LogType;
import com.tq.applogcollect.AppLogCollectProto.MethodAndObjectInfo;
import com.tq.applogcollect.AppLogCollectProto.MethodInfo;
import com.tq.applogcollect.AppLogCollectProto.ModuleInfo;
import com.tq.applogcollect.AppLogCollectProto.ObjectInfo;
import com.tq.applogcollect.AppLogCollectProto.UserDefinedMessage;
import com.tq.applogcollect.storage.LogStorage;
import com.tq.applogcollect.storage.SimpleBlockStorage;
import com.tq.applogcollect.utility.StringUtil;
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
