package com.tq.microservice;

import com.tq.microservice.annotation.Executable;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;
import java.lang.annotation.Annotation;
import java.util.jar.JarInputStream;
import java.util.jar.JarEntry;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.net.URLClassLoader;
import java.net.URL;
import java.lang.reflect.*;

public class App {
  private static String[] commandlineArguments;
  
  public static void main(String... args) {
    commandlineArguments = args;
    Throwable throwable = new Throwable();
    StackTraceElement[] stacks = throwable.getStackTrace();

    if (stacks.length < 2) {
      throw new RuntimeException("unknown fatal error");
    }

    int upFrameLevel = 1;
    StackTraceElement upFrame = stacks[upFrameLevel];

    try {
      URLClassLoader classLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
      Class startClass = classLoader.loadClass(upFrame.getClassName());
      String startScanPackage = startClass.getPackage().getName();

      for (URL path: classLoader.getURLs()) {
        File file = new File(path.getFile());
            
        if (file.isFile()) {
          scanJarFile(file, startScanPackage);
        }

        if (file.isDirectory()) {
          scanClassDirectory(file, startScanPackage);
        }
      }
    } catch (Exception e) {
      // ClassNotFoundException
      throw new RuntimeException(e);
    }
  }

  public static void walk(Path root, List<File> result) throws IOException {
    List<File> children = Files.list(root)
      .map(Path::toFile)
      .collect(Collectors.toList());

    result.addAll(children);

    for (File file: children) {
      if (file.isDirectory()) {
        walk(file.toPath(), result);
      }
    }
  }

  public static List<File> walk(Path root) throws IOException {
    List<File> result = new ArrayList<>();
    walk(root, result);
    return result;
  }

  public static void scanClassDirectory(File classDirectory, String startScanPackage) {
    try {
      List<File> descendants = walk(classDirectory.toPath());
      for (File file: descendants) {
        if (file.getName().endsWith(".class")) {
          String relativeName = file.getAbsolutePath().replace(classDirectory.getAbsolutePath(), "");
          if (relativeName.startsWith("\\")) {
            relativeName = relativeName.substring(1);
          }
          if (relativeName.startsWith("/")) {
            relativeName = relativeName.substring(1);
          }

          String className = getClassNameFromFileName(relativeName);
          if (!className.startsWith(startScanPackage)) {
            continue;
          }

          processScannedClass(className);
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

  public static void scanJarFile(File jarFile, String startScanPackage) {

    try {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      
      // IOExcepiton
      // FileNotFoundException
      JarInputStream jarStream = new JarInputStream(new FileInputStream(jarFile));
      for (JarEntry jarEntry = jarStream.getNextJarEntry();
           jarEntry != null;
           jarEntry = jarStream.getNextJarEntry()) {

        if (jarEntry.isDirectory()) {
          continue;
        }

        String entryName = jarEntry.getName();
        if (!entryName.endsWith(".class")) {
          continue;
        }
        
        String className = getClassNameFromFileName(entryName);
        if (!className.startsWith(startScanPackage)) {
          continue;
        }
        
        processScannedClass(className);
        
      }
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

  public static String getClassNameFromFileName(String fileName) {
    return fileName.replace("/", ".")
      .replace("\\", ".")
      .replace(".class", "");
  }

  public static Object getBean(String className) {
    return beans.get(className);
  }

  public static Object getOrCreateBean(Class clazz) throws Exception {
    String name = clazz.getName();
    if (beans.containsKey(name)) {
      return beans.get(name);
    }
    
    Object instance = clazz.newInstance();
    beans.put(name, instance);
    return instance;
  }

  public static HashMap<String,Object> beans = new HashMap<>();
  public static void processScannedClass(String className) throws Exception {
        
    Class clazz = Thread.currentThread()
      .getContextClassLoader()
      .loadClass(className);

    // for (Field field: clazz.getDeclaredFields()) {
    //   System.out.println(className + field);
    // Configuration anno = (Configuration) field.getAnnotation(Configuration.class);
    // if (anno == null) {
    //   continue;
    // }

    // String service = anno.service();

    // ManagedChannel channel;
    // ConfigurationServiceGrpc.ConfigurationServiceBlockingStub blockingStub;
    // ConfigurationServiceGrpc.ConfigurationServiceStub stub;
  
    // String host = "localhost";
    // int port = 12345; 
    // channel = ManagedChannelBuilder.forAddress(host, port)
    //   .usePlaintext()
    //   .build();
    // blockingStub = ConfigurationServiceGrpc.newBlockingStub(channel);
    // stub = ConfigurationServiceGrpc.newStub(channel);

    
    // String config = blockingStub.queryConfiguration(QueryConfigurationRequest.newBuilder()
    //                                                 .setServiceName("com.tq.foo")
    //                                                 .setServiceVersion("1.0")
    //                                                 .setConfigurationVersion("")
    //                                                 .build())
    //   .getConfigurationContent();

    // Object value = config;

    // channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    // String value = "TODO";
    // Object bean = getOrCreateBean(clazz);
    // field.setAccessible(true);
    // field.set(bean, value);
//    }

    try {
      Annotation anno = clazz.getAnnotation(Executable.class);
      if (anno != null) {
        Method method = clazz.getMethod("execute", (new String[]{}).getClass());
        method.invoke(getOrCreateBean(clazz), (Object) commandlineArguments);
      }
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }
}
