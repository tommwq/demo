package com.tq.gateway;

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
import org.springframework.beans.factory.annotation.Autowired;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import java.io.*;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import com.tq.gateway.service.builder.ProxyServiceBuilder;

@SpringBootApplication
public class GatewayServiceApplication implements CommandLineRunner {

  @Autowired
  GatewayConfig gatewayConfig;
  
  public static void main(String[] args) {
    SpringApplication.run(GatewayServiceApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println(gatewayConfig.toString());

    ClassLoader classLoader = new ProxyServiceBuilder().build(gatewayConfig);

    int port = gatewayConfig.getPort();
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
          String sourceFileName = gatewayConfig.getBuildDirectory() + "/gen/src/" + packageName.replace(".", "/") + "/" + className + ".java";

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
