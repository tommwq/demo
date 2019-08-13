package com.tq.applogcollect;

import com.tq.applogcollect.AppLogCollectProto.LogLevel;
import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.AppLogCollectProto.LogQueryCommand;
import com.tq.applogcollect.AppLogCollectProto.ModuleVersion;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.TreeMap;
import java.util.ArrayList;

class Logger {

  public static ArrayList<LogRecord> logBuffer = new ArrayList<>();

  // TODO make it singletong
  // TODO mutex
  private static long sequence = 1;
  // TODO add setter
  private static String appVersion = "";
  private static TreeMap<String,String> moduleVersions = new TreeMap<>();

  public static enum Level {
    Debug;
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
           Level.Debug.ordinal(),
           System.currentTimeMillis(),
           packageName,
           fileName,
           lineNumber,
           className,
           methodName,
           StringUtils.stringify(parameters),
           StringUtils.stringify(result));

    // TODO 将record投递到后台线程。
    System.out.println(record);
    logBuffer.add(record);

    return lsn;
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

    return LogRecord.newBuilder()
      .setSequence(sequence)
      .setAssociatedSequence(associatedSequence)
      // .setLogLevel()
      .setLocalTime(localTime)
      .setAppVersion(appVersion)
      //.setModuleVersions()
      .setSourceFile(fileName)
      .setLineNumber(lineNumber)
      .setPackageName(packageName)
      .setClassName(className)
      .setMethodName(methodName)
      // .setMethodParameters()
      .setMethodResult(result)
      .setDeviceId("abc")
      .build();
    
  }
}
