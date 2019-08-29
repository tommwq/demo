package com.tq.microservice.registryservice;

public class RegistryServiceConfig {
  
  private int port;
  private String adapter;

  public int getPort() {
    return port;
  }

  public RegistryServiceConfig setPort(int port) {
    this.port = port;
    return this;
  }

  public String getAdapter() {
    return adapter;
  }

  public RegistryServiceConfig setAdapter(String aAdapter) {
    this.adapter = aAdapter;
    return this;
  }
}
