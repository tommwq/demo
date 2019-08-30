package com.tq.microservice.configurationservice.adapter;

public interface ConfigurationServiceAdapter {
  // TODO add init method
  void createConfiguration(String serviceName,
              String serviceVersion,
              String configurationVersion,
              String configurationContent);
  String readConfiguration(String serviceName, String serviceVersion, String configurationVersion);
}
