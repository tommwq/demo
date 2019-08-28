package com.tq.microservice.registryservice.adapter;

import java.util.Set;

public interface RegistryServiceAdapter {
  
  void register(String serviceName, String serviceVersion, InstanceId instanceId);
  void unregister(InstanceId instanceId);
  Set<InstanceId> query(String serviceName, String serviceVersion);
}
