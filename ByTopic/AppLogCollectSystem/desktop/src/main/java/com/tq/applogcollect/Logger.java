package com.tq.applogcollect;

import com.tq.applogcollect.AppLogCollectProto.LogLevel;
import com.tq.applogcollect.AppLogCollectProto.LogQueryCommand;
import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.AppLogCollectProto.ModuleVersion;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedTransferQueue;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Logger {

  public static class Config {
    private String appVersion = "";
    private TreeMap<String,String> moduleVersions = new TreeMap<>();
    private String deviceId = "";
    private String fileName = "";
    private int blockCount = 16;
    private int blockSize = 4096;

    public int getBlockSize() {
      return blockSize;
    }

    public void setBlockSize(int size) {
      blockSize = size;
    }

    public int getBlockCount() {
      return blockCount;
    }

    public void setBlockCount(int count) {
      blockCount = count;
    }

    public String getFileName() {
      return fileName;
    }

    public void setFileName(String aFileName) {
      fileName = aFileName;
    }
    
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

  private static Logger instance = new Logger();

  private Config config = new Config();
  private long sequence = 1;
  private LinkedTransferQueue<LogRecord> logQueue = new LinkedTransferQueue<>();
  private Thread backgroundWriteThread = null;
  private LoggerStorage storage;
  
  private Logger() {}

  private static class BackgroundWriteRoutine implements Runnable {
    @Override
    public void run() {
      try {
        while (true) {
          LogRecord log = instance.logQueue.take();
          try {
            instance.write(log);
          } catch (IOException e) {
            // TODO
          }
        }
      } catch (InterruptedException e) {
        ArrayList<LogRecord> tails = new ArrayList<>();
        tails.addAll(instance.logQueue);
        tails.stream().forEach(log -> {try {instance.write(log);} catch (Exception ex){}});
      }
    }
  }

  public static Logger instance() {
    return instance;
  }

  public void open(Config aConfig) throws IOException, FileNotFoundException {
    config = aConfig;

    storage = new LoggerStorage(new SimpleBlockStorage(Paths.get(config.getFileName()),
                                                       config.getBlockCount(),
                                                       config.getBlockSize()));

    storage.open();
    
    backgroundWriteThread = new Thread(new BackgroundWriteRoutine());
    backgroundWriteThread.start();
  }

  public void close() {
    if (backgroundWriteThread != null) {
      backgroundWriteThread.interrupt();
    }
  }

  private void write(LogRecord log) throws IOException {
    storage.write(log);
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

    logQueue.offer(record);
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
