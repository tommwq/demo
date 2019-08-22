package com.tq.applogcollect;

import com.tq.applogcollect.AppLogCollectProto.LogLevel;
import com.tq.applogcollect.AppLogCollectProto.LogQueryCommand;
import com.tq.applogcollect.AppLogCollectProto.LogRecord;
import com.tq.applogcollect.AppLogCollectProto.ModuleVersion;
import com.tq.applogcollect.storage.LogStorage;
import com.tq.applogcollect.storage.SimpleBlockStorage;
import com.tq.applogcollect.utility.StringUtil;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.concurrent.LinkedTransferQueue;
import java.util.List;

public class Logger {

  private static Logger instance = new Logger();
  private AppInfo appInfo = new AppInfo();

  private long sequence = 1;
  private LinkedTransferQueue<LogRecord> logQueue = new LinkedTransferQueue<>();
  protected Thread backgroundWriteThread = null;
  private LogStorage storage;
  
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
      } finally {
        Logger.instance().backgroundWriteThread = null;
      }
    }
  }

  public static Logger instance() {
    return instance;
  }

  public void open(StorageConfig aConfig, AppInfo aInfo) throws IOException {
    appInfo = aInfo;

    storage = new LogStorage(new SimpleBlockStorage(Paths.get(aConfig.getFileName()),
            aConfig.getBlockCount(),
            aConfig.getBlockSize()));

    storage.open();

    Thread.UncaughtExceptionHandler next = Thread.currentThread().getUncaughtExceptionHandler();
    Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread location, Throwable error) {
          instance().error(location, error);
          
          if (next != null) {
            next.uncaughtException(location, error);
          }
        }
      });

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
          instance().close();
    }));
    
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

  public void leave(long associatedSequence, Object... parameters) {
    log(2, associatedSequence, parameters);
  }

  public void error(Object... parameters) {
    log(2, 0, parameters);
  }
  
  public void warn(Object... parameters) {
    log(2, 0, parameters);
  }
  
  public void info(Object... parameters) {
    log(2, 0, parameters);
  }
  
  public void debug(Object... parameters) {
    log(2, 0, parameters);
  }

  public List<LogRecord> queryLog(long sequence, int count) {
    return storage.read(sequence, count);
  }

  public long maxSequence() {
    return storage.maxSequence();
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
                                    StringUtil.stringify(parameters),
                                    StringUtil.stringify(result));

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
      .setAppVersion(appInfo.getAppVersion())
      .addAllModuleVersions(
        appInfo.getModuleVersions()
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
      .setDeviceId(appInfo.getDeviceId())
      .build();
  }
}
