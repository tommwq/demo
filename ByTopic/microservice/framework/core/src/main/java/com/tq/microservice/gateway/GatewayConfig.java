package com.tq.microservice.gateway;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Component
@ConfigurationProperties(prefix="gateway")
public class GatewayConfig {
  private String protocolDirectory = "";
  private String buildDirectory = "";
  private String protoCompilerPath = "";
  private String protoIncludeDirectory = "";
  private String grpcPluginPath = "";
  private String javaCompilerPath = "";
  private String jarPath = "";
  private int port = 50051;
  private String classPath = "";
  private String registryServiceAddress = "";

  public int getPort() {
    return port;
  }

  public void setPort(int value) {
    port = value;
  }
  
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

  public String getRegistryServiceAddress() {
    return registryServiceAddress;
  }

  public void setRegistryServiceAddress(String address) {
    registryServiceAddress = address;
  }

  @Override
  public String toString() {
    return String.join("\n", new String[] {
        "gateway.protocolDirectory=" + protocolDirectory,
        "gateway.buildDirectory=" + buildDirectory,
        "gateway.protoCompilerPath=" + protoCompilerPath,
        "gateway.protoIncludeDirectory=" + protoIncludeDirectory,
        "gateway.grpcPluginPath=" + grpcPluginPath,
        "gateway.javaCompilerPath=" + javaCompilerPath,
        "gateway.jarPath=" + jarPath,
        "gateway.port=" + port,
        "gateway.classPath=" + classPath,
      });
  }
}
