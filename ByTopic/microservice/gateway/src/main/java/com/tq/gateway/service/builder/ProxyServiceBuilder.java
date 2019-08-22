package com.tq.gateway.service.builder;

import com.tq.gateway.generator.ProxyServiceGenerator;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import com.tq.utility.CollectionUtils;
import com.tq.utility.OSUtils;
import com.tq.utility.Utils;
import freemarker.template.Configuration;
import io.grpc.stub.StreamObserver;
import java.io.Console;
import java.io.IOException;
import java.io.File;
import java.nio.file.Paths;
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
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class ProxyServiceBuilder {

  public static class Config {
    private String protocolDirectory = "";
    private String buildDirectory = "";
    private String protoCompilerPath = "";
    private String protoIncludeDirectory = "";
    private String grpcPluginPath = "";
    private String javaCompilerPath = "";
    private String jarPath = "";
    private String classPath = "";
  
    public String getProtocolDirectory() {
      return protocolDirectory;
    }
  
    public void setProtocolDirectory(String value) {
      protocolDirectory = value;
    }

    public String getBuildDirectory() {
      return buildDirectory;
    }
  
    public void setBuildDirectory(String value) {
      buildDirectory = value;
    }

    public String getProtoCompilerPath() {
      return protoCompilerPath;
    }
  
    public void setProtoCompilerPath(String value) {
      protoCompilerPath = value;
    }

    public String getGrpcPluginPath() {
      return grpcPluginPath;
    }
  
    public void setGrpcPluginPath(String value) {
      grpcPluginPath = value;
    }

    public String getJavaCompilerPath() {
      return javaCompilerPath;
    }
  
    public void setJavaCompilerPath(String value) {
      javaCompilerPath = value;
    }

    public String getJarPath() {
      return jarPath;
    }
  
    public void setJarPath(String value) {
      jarPath = value;
    }

    public String getClassPath() {
      return classPath;
    }
  
    public void setClassPath(String value) {
      classPath = value;
    }

    public String getProtoIncludeDirectory() {
      return protoIncludeDirectory;
    }

    public void setProtoIncludeDirectory(String value) {
      protoIncludeDirectory = value;
    }
  }

  public static final String DESCRIPTOR_SET_FILE_NAME = "descriptor_set.pbb";

  private String getDescriptorSetFileName(String buildDirectory) {
    return buildDirectory + "/" + DESCRIPTOR_SET_FILE_NAME;
  }

  private void compileGrpcFiles(String protoCompilerPath,
                                String protoIncludeDirectory,
                                String protocolDirectory,
                                String buildDirectory,
                                String grpcPluginPath)
    throws IOException, InterruptedException {
    OSUtils.createProcess(CollectionUtils.mergeList(Arrays.asList(protoCompilerPath,
                                                                  "--proto_path",
                                                                  protocolDirectory,
                                                                  "--proto_path",
                                                                  protoIncludeDirectory,
                                                                  "--descriptor_set_out",
                                                                  getDescriptorSetFileName(buildDirectory),
                                                                  "--java_out=" + buildDirectory),
                                                    OSUtils.recurseListProtocolFileNames(new File(protocolDirectory)))).waitFor();

    OSUtils.createProcess(CollectionUtils.mergeList(Arrays.asList(protoCompilerPath,
                                                                  "--proto_path",
                                                                  protocolDirectory,
                                                                  "--proto_path",
                                                                  protoIncludeDirectory,
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

  public void compile(Config config) throws Exception {

    String buildDirectory = config.getBuildDirectory();
    OSUtils.createDirectoryInNeed(config.getBuildDirectory());
    
    compileGrpcFiles(config.getProtoCompilerPath(),
                     config.getProtoIncludeDirectory(),
                     config.getProtocolDirectory(),
                     config.getBuildDirectory(),
                     config.getGrpcPluginPath());

    generateProxySourceFiles(config.getBuildDirectory(), getDescriptorSetFileName(buildDirectory));

    String classPath = String.join(";", Arrays.asList(((URLClassLoader) Thread.currentThread().getContextClassLoader()).getURLs())
                                   .stream()
                                   .map(URL::getFile)
                                   .collect(Collectors.toList()));
    classPath += ";" + config.getClassPath();

    compileJavaFiles(config.getJavaCompilerPath(),
                     config.getBuildDirectory(),
                     config.getBuildDirectory() + "/classes",
                     classPath);

    packageClassFiles(config.getJarPath(),
                      config.getBuildDirectory() + "/generated.jar",
                      config.getBuildDirectory() + "/classes");
  }

  public ClassLoader build(Config config) throws Exception {
    compile(config);
    ClassLoader classLoader = createProxyServiceLoader(config.getBuildDirectory() + "/generated.jar");
    return classLoader;
  }

  public static void main(String... args) {
    // TODO
  }
}
