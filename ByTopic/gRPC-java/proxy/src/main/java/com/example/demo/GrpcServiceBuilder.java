package com.example.demo;

import java.io.Console;
import java.io.IOException;
import java.io.File;
import java.nio.file.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import com.sun.tools.javac.main.Main;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import java.io.*;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;


public class GrpcServiceBuilder {

  public static class Config {
    public String protocolDirectory = "";
    public String buildDirectory = "";
    public String protoCompilerRootDirectory = "";
    public String grpcPluginPath = "";
    public String javaCompilerPath = "";
    public String classPath = "";
  }

  private void compileGrpcFiles(String protoCompilerRootDirectory,
                                String protocolDirectory,
                                String buildDirectory,
                                String grpcPluginPath)
    throws IOException, InterruptedException {
    // TODO 处理操作系统是Linux的情况。
    OSUtils.createProcess(CollectionUtils.mergeList(Arrays.asList(protoCompilerRootDirectory + "/bin/protoc.exe",
                                                                  "--proto_path",
                                                                  protocolDirectory,
                                                                  "--proto_path",
                                                                  protoCompilerRootDirectory + "/include/google/protobuf",
                                                                  "--descriptor_set_out",
                                                                  buildDirectory + "/a.d",
                                                                  "--java_out=" + buildDirectory),
                                                    OSUtils.recurseListProtocolFileNames(new File(protocolDirectory)))).waitFor();

    OSUtils.createProcess(CollectionUtils.mergeList(Arrays.asList(protoCompilerRootDirectory + "/bin/protoc.exe",
                                                                  "--proto_path",
                                                                  protocolDirectory,
                                                                  "--proto_path",
                                                                  protoCompilerRootDirectory + "/include/google/protobuf",
                                                                  "--grpc-java_out=" + buildDirectory,
                                                                  "--plugin=protoc-gen-grpc-java=" + grpcPluginPath),
                                                    OSUtils.recurseListProtocolFileNames(new File(protocolDirectory)))).waitFor();
  }


  private static void compileJavaFiles(String javaCompilerPath,
                                       String sourceDirectory,
                                       String outputDirectory,
                                       String classPath)
    throws IOException, InterruptedException {

    OSUtils.createDirectoryInNeed(outputDirectory);
    OSUtils.createProcess(CollectionUtils.mergeList(Arrays.asList(javaCompilerPath,
                                                                  "-d",
                                                                  outputDirectory,
                                                                  "-classpath",
                                                                  classPath),
                                                    OSUtils.recurseListJavaFileNames(sourceDirectory))).waitFor();
  }

  private static void packageClassFiles(String jarPath,
                                        String outputFilename,
                                        String classFileDirectory) throws IOException, InterruptedException {
  
    OSUtils.createProcess(CollectionUtils.mergeList(Arrays.asList(jarPath,
                                                                  "cf",
                                                                  outputFilename,
                                                                  "-C",
                                                                  classFileDirectory),
                                                    Stream.of(new File(classFileDirectory).listFiles((dir, name) -> Utils.not(name.startsWith("."))))
                                                    .map(File::getName)
                                                    .collect(Collectors.toList()))).waitFor();
  }

  private ClassLoader loadGrpcService(String jarFileName) {

    List<String> jarList = Arrays.asList(jarFileName,
                                         "C:/Users/guosen/.m2/repository/com/google/protobuf/protobuf-java/3.7.1/protobuf-java-3.7.1.jar",
                                         "C:/Users/guosen/.m2/repository/com/google/protobuf/protobuf-java-util/3.7.1/protobuf-java-util-3.7.1.jar",
                                         "C:/Users/guosen/.m2/repository/com/google/protobuf/protobuf-java/3.7.1/protobuf-java-3.7.1.jar",
                                         "C:/Users/guosen/.m2/repository/com/google/protobuf/protobuf-java-util/3.7.1/protobuf-java-util-3.7.1.jar",
                                         "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-api/1.22.1/77311e5735c4097c5cce57f0f4d0847c51db63bb/grpc-api-1.22.1.jar",
                                         "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-context/1.22.1/1a074f9cf6f367b99c25e70dc68589f142f82d11/grpc-context-1.22.1.jar",
                                         "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-core/1.22.1/f8b6f872b7f069aaff1c3380b2ba7f91f06e4da1/grpc-core-1.22.1.jar",
                                         "D:/workspace/project/study/grpc-java/netty/build/libs/grpc-netty-1.21.0-SNAPSHOT.jar",
                                         "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-protobuf/1.21.0/ac92a46921f9bf922e76b46e5731eaf312545acb/grpc-protobuf-1.21.0.jar",
                                         "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-stub/1.22.1/910550293aab760b706827c5f71c80551e5490f3/grpc-stub-1.22.1.jar",
                                         "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/com.google.guava/guava/27.0.1-jre/bd41a290787b5301e63929676d792c507bbc00ae/guava-27.0.1-jre.jar",
                                         "D:/workspace/project/study/bazel/third_party/javax_annotations/javax.annotation-api-1.3.2.jar"
      );
       
    try {
      URLClassLoader classLoader = new URLClassLoader(jarList.stream()
                                                      .map((fileName) -> {
                                                          try {
                                                            return new File(fileName).toURI().toURL();
                                                          } catch (MalformedURLException e) {
                                                            return null;
                                                          }
                                                        })
                                                      .filter((url) -> url != null)
                                                      .collect(Collectors.toList())
                                                      .toArray(new URL[]{}),
                                                      Thread.currentThread().getContextClassLoader());

      String packageName = "com.tq.test.helloworld";
      String outerClassName = "HelloWorldProto";
      String serviceName = "Greeter";
      String className = String.format("%s.%sGrpc$%sImplBase", packageName, serviceName, serviceName);
      return classLoader;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public ClassLoader build(Config config) throws Exception {
    String protocolDirectory = config.protocolDirectory;

    String buildDirectory = config.buildDirectory;
    OSUtils.createDirectoryInNeed(buildDirectory);
    compileGrpcFiles(config.protoCompilerRootDirectory,
                     config.protocolDirectory,
                     config.buildDirectory,
                     config.grpcPluginPath);

    compileJavaFiles(config.javaCompilerPath,
                     config.buildDirectory,
                     config.buildDirectory + "/classes",
                     config.classPath);

    packageClassFiles("C:/Program Files (x86)/Java/jdk1.8.0_192/bin/jar.exe",
                      config.buildDirectory + "/a.jar",
                      config.buildDirectory + "/classes");
                      
    ClassLoader classLoader = loadGrpcService(config.buildDirectory + "/a.jar");
    return classLoader;
  }
}
