package com.tq.applogcollect;

import com.google.protobuf.Any;
import com.tq.applogcollect.AppLogCollectProto.Command;
import com.tq.applogcollect.AppLogCollectProto.DeviceAndAppInfo;
import com.tq.applogcollect.AppLogCollectProto.Log;
import com.tq.applogcollect.AppLogCollectProto.LogHeader;
import com.tq.applogcollect.AppLogCollectProto.LogType;
import com.tq.applogcollect.AppLogCollectProto.ModuleInfo;
import com.tq.applogcollect.storage.LogStorage;
import com.tq.applogcollect.storage.SimpleBlockStorage;
import com.tq.applogcollect.utility.StringUtil;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.LinkedTransferQueue;
import java.util.List;
import java.util.stream.Collectors;

public class Logger {

  private static Logger instance = new Logger();

  private DeviceAndAppConfig info = new DeviceAndAppConfig();
  private long sequence = 1;
  private LinkedTransferQueue<Log> logQueue = new LinkedTransferQueue<>();
  protected Thread backgroundWriteThread = null;
  private LogStorage storage;
  
  private Logger() {}

  private static class BackgroundWriteRoutine implements Runnable {
    @Override
    public void run() {
      try {
        while (true) {
          Log log = instance.logQueue.take();
          try {
            instance.write(log);
          } catch (IOException e) {
            // TODO
          }
        }
      } catch (InterruptedException e) {
        ArrayList<Log> tails = new ArrayList<>();
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

  public void open(StorageConfig aConfig, DeviceAndAppConfig aInfo) throws IOException {
    info = aInfo;

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
      try {
        backgroundWriteThread.join();
      } catch (InterruptedException e) {
        // ignore
      }
    }
  }

  private void write(Log log) throws IOException {
    storage.write(log);
  }

  public void trace() {
    log(2, 0, new Object[]{});
  }
  
  public void log(String message, Object... parameters) {
    log(2, 0, parameters);
  }

  public void dump(Object... parameters) {
    log(2, 0, parameters);
  }

  public void error(Object... parameters) {
    log(2, 0, parameters);
  }

  public List<Log> queryLog(long sequence, int count) {
    return storage.read(sequence, count);
  }

  public long maxSequence() {
    return storage.maxSequence();
  }

  // TODO think concurrency
  private long nextSequence() {
    return sequence++;
  }

  private long currentTime() {
    return System.currentTimeMillis();
  }
  
  private Log newDeviceAndAppInfoLog(DeviceAndAppConfig info) {
    return Log.newBuilder()
      .setHeader(LogHeader.newBuilder()
                 .setSequence(nextSequence())
                 .setTime(currentTime())
                 .setLogType(LogType.DEVICE_AND_APP_INFO)
                 .build())
      .setBody(Any.pack(DeviceAndAppInfo.newBuilder()
                        .setDeviceId(info.getDeviceId())
                        .setDeviceVersion(info.getDeviceVersion())
                        .setBaseOsName(info.getBaseOsName())
                        .setBaseOsVersion(info.getBaseOsVersion())
                        .setOsName(info.getOsName())
                        .setOsVersion(info.getOsVersion())
                        .setAppVersion(info.getAppVersion())
                        .addAllModuleInfo(info.getModuleVersions()
                                          .entrySet()
                                          .stream()
                                          .map(entry -> ModuleInfo.newBuilder()
                                               .setModuleName(entry.getKey())
                                               .setModuleVersion(entry.getValue())
                                               .build())
                                          .collect(Collectors.toList()))
                        .build()))
      .build();
  }
  
  // private Log newMethodAndObjectInfoLog() {}
  // private Log newExceptionInfoLog() {}
  // private Log newUserDefinedLog() {}
  
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
    Log record = newLog(lsn,
                        associatedSequence,
                        0,
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
    
  private Log newLog(long sequence,
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

    return Log.newBuilder()
//      .setSequence(sequence)
//      .setAssociatedSequence(associatedSequence)
      //    .setLocalTime(localTime)
      // .setAppVersion(info.getAppVersion())
      // .addAllModuleVersions(
      //   info.getModuleVersions()
      //   .entrySet()
      //   .stream()
      //   .map(entry -> ModuleVersion.newBuilder()
      //        .setModuleName(entry.getKey())
      //        .setModuleVersion(entry.getValue())
      //        .build())
      //   .collect(Collectors.toList()))
      // .setSourceFile(fileName)
      // .setLineNumber(lineNumber)
      // .setPackageName(packageName)
      // .setClassName(className)
      // .setMethodName(methodName)
      // .addAllMethodParameters(Stream.of(parameters)
      //                         .collect(Collectors.toList()))
      // .setMethodResult(result)
      // .setDeviceId(info.getDeviceId())
      .build();
  }
}
