package com.tq.gateway;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Component
@ConfigurationProperties(prefix="gateway")
public class GatewayConfig {
  private String protocolDirectory = "";
  private String buildDirectory = "";
  private String protoCompilerRootDirectory = "";
  private String grpcPluginPath = "";
  private String javaCompilerPath = "";
  private String classPath = "";
  private int port = 50051;
  // TODO host, port, ...

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

  public String getProtoCompilerRootDirectory() {
    return protoCompilerRootDirectory;
  }
  
  public void setProtoCompilerRootDirectory(String value) {
    protoCompilerRootDirectory = value;
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

  public String getClassPath() {
    return classPath;
  }
  
  public void setClassPath(String value) {
    classPath = value;
  }

  @Override
  public String toString() {
    return String.join("\n", new String[] {
        "gateway.protocolDirectory=" + protocolDirectory,
        "gateway.buildDirectory=" + buildDirectory,
        "gateway.protoCompilerRootDirectory=" + protoCompilerRootDirectory,
        "gateway.grpcPluginPath=" + grpcPluginPath,
        "gateway.javaCompilerPath=" + javaCompilerPath,
        "gateway.classPath=" + classPath,
        "gateway.port=" + port,
      });
  }
}
