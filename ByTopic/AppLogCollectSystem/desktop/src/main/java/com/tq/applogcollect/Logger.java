package com.tq.applogcollect;

import com.tq.applogcollect.AppLogCollectProto.LogLevel;
import com.tq.applogcollect.AppLogCollectProto.LogQueryCommand;
import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.AppLogCollectProto.ModuleVersion;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Logger {

  public static class Config {
    private String appVersion = "";
    private TreeMap<String,String> moduleVersions = new TreeMap<>();
    private String deviceId = "";

    public String getAppVersion() {
      return appVersion;
    }
    
    public void setAppVersion(String aVersion) {
      appVersion = aVersion;
    }

    public Map<String, String> getModuleVersions() {
      return moduleVersions;
    }

    public void setModuleVersion(String module, String version) {
      moduleVersions.put(module, version);
    }

    public String getDeviceId() {
      return deviceId;
    }

    public void setDeviceId(String aDeviceId) {
      deviceId = aDeviceId;
    }
  }

  // TODO consider concurrency
  private static Config config = new Config();
  private static long sequence = 1;
  private static Logger instance = new Logger();
  private static ArrayList<LogRecord> logBuffer = new ArrayList<>();
  
  private Logger() {}

  public static void backgroundWriteRoutine() {
  }

  public static Logger instance() {
    return instance;
  }

  public static void changeConfig(Config aConfig) {
    config = aConfig;
  }

  public static List<LogRecord> getLogBuffer() {
    return logBuffer;
  }

  public static LogRecord lastLog() {
    return logBuffer.isEmpty() ? null : logBuffer.get(logBuffer.size() - 1);
  }
    
  public long enter(Object... parameters) {
    return log(2, 0, parameters);
  }

  public long leave(long associatedSequence, Object... parameters) {
    return log(2, associatedSequence, parameters);
  }
  
  private long log(int stackDepth, long associatedSequence, Object... parameters) {

    String fileName = "unknown";
    int lineNumber = -1;
    String packageName = "unknown";
    String methodName = "unknown";
    String className = "unknown";
    Object result = null;
        
    Throwable throwable = new Throwable();
    StackTraceElement[] stacks = throwable.getStackTrace();
        
    if (stacks.length > stackDepth) {
      StackTraceElement stack = stacks[stackDepth];
      fileName = stack.getFileName();
      lineNumber = stack.getLineNumber();
      packageName = "";
      methodName = stack.getMethodName();
      className = stack.getClassName();

      int classNamePosition = className.lastIndexOf('.');
      if (classNamePosition != -1) {
        packageName = className.substring(0, classNamePosition);
        className = className.substring(classNamePosition + 1);
      }
    }

    long lsn = sequence++;
    LogRecord record = newLogRecord(lsn,
                                    associatedSequence,
                                    LogLevel.DEBUG.ordinal(),
                                    System.currentTimeMillis(),
                                    packageName,
                                    fileName,
                                    lineNumber,
                                    className,
                                    methodName,
                                    StringUtils.stringify(parameters),
                                    StringUtils.stringify(result));

    logBuffer.add(record);
    return lsn;
  }
  
  public LogRecord newEmptyLogRecord() {
    return newLogRecord(0,
                        0,
                        LogLevel.DEBUG.ordinal(),
                        System.currentTimeMillis(),
                        "",
                        "",
                        0,
                        "",
                        "",
                        new String[]{""},
                        "");
  }
  
  private LogRecord newLogRecord(long sequence,
                                 long associatedSequence,
                                 int level,
                                 long localTime,
                                 String packageName,
                                 String fileName,
                                 int lineNumber,
                                 String className,
                                 String methodName,
                                 String[] parameters,
                                 String result) {

    LogLevel logLevel = LogLevel.DEBUG;
    if (level <= LogLevel.FATAL.ordinal() || LogLevel.TRACE.ordinal() <= level) {
      logLevel = LogLevel.values()[level];
    }

    return LogRecord.newBuilder()
      .setSequence(sequence)
      .setAssociatedSequence(associatedSequence)
      .setLogLevel(logLevel)
      .setLocalTime(localTime)
      .setAppVersion(config.getAppVersion())
      .addAllModuleVersions(
        config.getModuleVersions()
        .entrySet()
        .stream()
        .map(entry -> ModuleVersion.newBuilder()
             .setModuleName(entry.getKey())
             .setModuleVersion(entry.getValue())
             .build())
        .collect(Collectors.toList()))
      .setSourceFile(fileName)
      .setLineNumber(lineNumber)
      .setPackageName(packageName)
      .setClassName(className)
      .setMethodName(methodName)
      .addAllMethodParameters(Stream.of(parameters)
                              .collect(Collectors.toList()))
      .setMethodResult(result)
      .setDeviceId(config.getDeviceId())
      .build();
  }
}
