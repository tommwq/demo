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
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import com.sun.tools.javac.main.Main;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import java.io.*;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import com.google.protobuf.DescriptorProtos.FileDescriptorSet;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

  @Override
  public void run(String... args) throws Exception {

    // 配置
    GrpcServiceBuilder.Config config = new GrpcServiceBuilder.Config();
    config.protocolDirectory = "d:/workspace/project/demo/ByTopic/gRPC-java/protocol";
    config.buildDirectory = "d:/workspace/project/demo/ByTopic/gRPC-java/build";
    config.protoCompilerRootDirectory = "D:/Program Files/protoc-3.6.1-win32";
    config.grpcPluginPath = "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/protoc-gen-grpc-java/1.22.1/e0f304c1f3a7892543de91a3b6d7be4f0409257f/protoc-gen-grpc-java-1.22.1-windows-x86_64.exe";
    config.javaCompilerPath = "C:/Program Files (x86)/Java/jdk1.8.0_192/bin/javac.exe";
    config.classPath = ".;C:/Users/guosen/.m2/repository/com/google/protobuf/protobuf-java/3.7.1/protobuf-java-3.7.1.jar;C:/Users/guosen/.m2/repository/com/google/protobuf/protobuf-java-util/3.7.1/protobuf-java-util-3.7.1.jar;C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-api/1.22.1/77311e5735c4097c5cce57f0f4d0847c51db63bb/grpc-api-1.22.1.jar;C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-context/1.22.1/1a074f9cf6f367b99c25e70dc68589f142f82d11/grpc-context-1.22.1.jar;C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-core/1.22.1/f8b6f872b7f069aaff1c3380b2ba7f91f06e4da1/grpc-core-1.22.1.jar;D:/workspace/project/study/grpc-java/netty/build/libs/grpc-netty-1.21.0-SNAPSHOT.jar;C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-protobuf/1.21.0/ac92a46921f9bf922e76b46e5731eaf312545acb/grpc-protobuf-1.21.0.jar;C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-stub/1.22.1/910550293aab760b706827c5f71c80551e5490f3/grpc-stub-1.22.1.jar;C:/Users/guosen/.gradle/caches/modules-2/files-2.1/com.google.guava/guava/27.0.1-jre/bd41a290787b5301e63929676d792c507bbc00ae/guava-27.0.1-jre.jar;D:/workspace/project/study/bazel/third_party/javax_annotations/javax.annotation-api-1.3.2.jar";


    ClassLoader classLoader = new GrpcServiceBuilder().build(config);

    int port = 50051;
    ServerBuilder serverBuilder = ServerBuilder.forPort(port);
    serverBuilder.fallbackHandlerRegistry(new HandlerRegistry() {
        @Override
        public ServerMethodDefinition<?,?> lookupMethod(String methodName, String authority) {

          int index = methodName.indexOf("/");
          if (index == -1) {
            return null;
          }

          String fullClassName = methodName.substring(0, index);
          String classMethodName = methodName.substring(index + 1);
          String packageName = "";
          String className = fullClassName;
          index = fullClassName.lastIndexOf(".");
          if (index != -1){
            packageName = fullClassName.substring(0, index);
            className = fullClassName.substring(index + 1);
          }

          String implClassName = String.format("%s.%sGrpc.%sImplBase", packageName, className, className);
          String sourceCode = String.format("package %s;import %s;public class %s extends %s {}", packageName, implClassName, className, implClassName);
          String sourceFileName = config.buildDirectory + "/gen/src/" + packageName.replace(".", "/") + "/" + className + ".java";

          try {
            String serviceClassName = packageName + "." + className;
            BindableService serviceInstance = (BindableService) classLoader.loadClass(serviceClassName).newInstance();
            System.out.println(serviceClassName);
            return serviceInstance.bindService().getMethod(methodName);
          } catch (Exception e) {
            e.printStackTrace();
          }
          return null;
        }
      });

    System.out.println("start");
    Server server = serverBuilder.build().start();

    Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          server.shutdown();
        }
      });
    
    server.awaitTermination();
  }

}
