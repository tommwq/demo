package com.tq.gateway.service.builder;

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
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import java.io.*;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;

import com.tq.utility.OSUtils;
import com.tq.utility.Utils;
import com.tq.utility.CollectionUtils;
import com.tq.gateway.GatewayConfig;

import com.tq.gateway.generator.ProxyServiceGenerator;
import freemarker.template.Configuration;


public class ProxyServiceBuilder {

  public static final String DESCRIPTOR_SET_FILE_NAME = "descriptor_set.pbb";

  private String getDescriptorSetFileName(String buildDirectory) {
    return buildDirectory + "/" + DESCRIPTOR_SET_FILE_NAME;
  }

  private void compileGrpcFiles(String protoCompilerRootDirectory,
                                String protocolDirectory,
                                String buildDirectory,
                                String grpcPluginPath)
    throws IOException, InterruptedException {
    OSUtils.createProcess(CollectionUtils.mergeList(Arrays.asList(protoCompilerRootDirectory + "/bin/protoc.exe",
                                                                  "--proto_path",
                                                                  protocolDirectory,
                                                                  "--proto_path",
                                                                  protoCompilerRootDirectory + "/include/google/protobuf",
                                                                  "--descriptor_set_out",
                                                                  getDescriptorSetFileName(buildDirectory),
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
                                        String classFileDirectory)
    throws IOException, InterruptedException {
  
    OSUtils.createProcess(CollectionUtils.mergeList(Arrays.asList(jarPath,
                                                                  "cf",
                                                                  outputFilename,
                                                                  "-C",
                                                                  classFileDirectory),
                                                    Stream.of(new File(classFileDirectory).listFiles((dir, name) -> Utils.not(name.startsWith("."))))
                                                    .map(File::getName)
                                                    .collect(Collectors.toList()))).waitFor();
  }

  private ClassLoader createProxyServiceLoader(String jarFileName) throws Exception {

    List<String> jarList = Arrays.asList(jarFileName);
    URLClassLoader classLoader = new URLClassLoader(jarList.stream()
                                                    .map(Utils::fileNameToUrl)
                                                    .filter(Utils::notNull)
                                                    .collect(Collectors.toList())
                                                    .toArray(new URL[]{}),
                                                    Thread.currentThread().getContextClassLoader());
    return classLoader;
  }

  private void generateProxySourceFiles(String outputDirectory, String descriptorSetFileName) throws Exception {
    Configuration freemarkerConfig = new Configuration(Configuration.VERSION_2_3_27);
    freemarkerConfig.setClassLoaderForTemplateLoading(Thread.currentThread().getContextClassLoader(), "/template");
    
    ProxyServiceGenerator.newBuilder()
      .setOutputDirectory(outputDirectory)
      .setFreemarkerConfiguration(freemarkerConfig)
      .setDescriptorSetFileName(descriptorSetFileName)
      .build()
      .generate();
  }

  public ClassLoader build(GatewayConfig config) throws Exception {

    String buildDirectory = config.getBuildDirectory();
    OSUtils.createDirectoryInNeed(config.getBuildDirectory());
    
    compileGrpcFiles(config.getProtoCompilerRootDirectory(),
                     config.getProtocolDirectory(),
                     config.getBuildDirectory(),
                     config.getGrpcPluginPath());

    generateProxySourceFiles(config.getBuildDirectory(), getDescriptorSetFileName(buildDirectory));

    String classPath = String.join(";", Arrays.asList(((URLClassLoader) Thread.currentThread().getContextClassLoader()).getURLs())
                                   .stream()
                                   .map(URL::getFile)
                                   .collect(Collectors.toList()));

    System.out.println(classPath);
    compileJavaFiles(config.getJavaCompilerPath(),
                     config.getBuildDirectory(),
                     config.getBuildDirectory() + "/classes",
                     classPath);

    packageClassFiles(config.getJarPath(),
                      config.getBuildDirectory() + "/generated.jar",
                      config.getBuildDirectory() + "/classes");
                      
    ClassLoader classLoader = createProxyServiceLoader(config.getBuildDirectory() + "/generated.jar");
    return classLoader;
  }
}
