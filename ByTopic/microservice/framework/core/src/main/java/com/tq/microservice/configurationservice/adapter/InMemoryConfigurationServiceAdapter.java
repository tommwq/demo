package com.tq.microservice.configurationservice.adapter;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryConfigurationServiceAdapter implements ConfigurationServiceAdapter {

  private ConcurrentHashMap<String,String> configurationTable = new ConcurrentHashMap<>();
  
  // TODO add init method
  public void createConfiguration(String serviceName,
                                  String serviceVersion,
                                  String configurationVersion,
                                  String configurationContent) {

    String key = generateKey(serviceName,
                             serviceVersion,
                             configurationVersion);

    configurationTable.put(key, configurationContent);
  }
  
  public String readConfiguration(String serviceName, String serviceVersion, String configurationVersion) {
    String key = generateKey(serviceName,
                             serviceVersion,
                             configurationVersion);

    if (configurationTable.containsKey(key)) {
      return configurationTable.get(key);
    }

    return "";
  }

  private String generateKey(String serviceName, String serviceVersion, String configurationVersion) {
    return String.format("%s:%s:%s",
                         serviceName,
                         serviceVersion,
                         configurationVersion);
  }
}
