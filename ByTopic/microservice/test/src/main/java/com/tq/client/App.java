package com.tq.client;

import com.tq.configurationservice.QueryConfigurationRequest;
import com.tq.configurationservice.QueryConfigurationReply;
import com.tq.configurationservice.PostConfigurationRequest;
import com.tq.configurationservice.PostConfigurationReply;
import com.tq.configurationservice.ConfigurationServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
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



public class App {

  public static void run() {
    Throwable throwable = new Throwable();
    StackTraceElement[] stacks = throwable.getStackTrace();

    if (stacks.length < 2) {
      throw new RuntimeException("invalid stack");
    }

    int upFrameLevel = 1;
    StackTraceElement stack = stacks[upFrameLevel];

    try {
      URLClassLoader urlClassLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
      Class startClass = urlClassLoader.loadClass(stack.getClassName());
      String startScanPackage = startClass.getPackage().getName();

      URL[] urlArray = urlClassLoader.getURLs();

      Stream.of(urlArray).forEach(url -> {
          File file = new File(url.getFile());
            
          if (file.isFile()) {
            scanJarFile(file, startScanPackage);
          }

          if (file.isDirectory()) {
            scanClassDirectory(file, startScanPackage);
          }
        });
      
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

  public static void processScannedClass(String className) throws ClassNotFoundException {
    System.out.println("CLASS: " + className);
    
    Class clazz = Thread.currentThread()
      .getContextClassLoader()
      .loadClass(className);
    
    Annotation annotationArray[] = clazz.getAnnotations();
    for (Annotation anno: annotationArray) {
      System.out.println(anno.annotationType().getName());
    }

    Stream.of(clazz.getAnnotatedInterfaces())
      .forEach(x -> System.out.println(x.getType().getTypeName()));
  }
}
