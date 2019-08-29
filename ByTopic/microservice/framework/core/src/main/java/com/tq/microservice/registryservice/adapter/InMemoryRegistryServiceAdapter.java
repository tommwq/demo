package com.tq.microservice.registryservice.adapter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * maintain service instance locations in memory.
 */
public class InMemoryRegistryServiceAdapter implements RegistryServiceAdapter {

  private static final String WILDCARD = "";

  private static class ServiceDescriptor {
    public String name;
    public String version;
    public ServiceDescriptor(String aName, String aVersion) {
      name = aName;
      version = aVersion;
    }
  }

  // name -> version -> instance
  private Map<String, Map<String, Set<InstanceId>>> registryTable = new ConcurrentHashMap<>();
  // instance -> service descriptor
  private Map<InstanceId, ServiceDescriptor> instanceTable = new ConcurrentHashMap<>();

  @Override
  public void register(String serviceName, String serviceVersion, InstanceId instanceId) {
    if (!registryTable.containsKey(serviceName)) {
      registryTable.put(serviceName, new ConcurrentHashMap<>());
    }

    if (!registryTable.get(serviceName).containsKey(serviceVersion)) {
      registryTable.get(serviceName).put(serviceVersion, new ConcurrentSkipListSet<>());
    }

    registryTable.get(serviceName).get(serviceVersion).add(instanceId);
    instanceTable.put(instanceId, new ServiceDescriptor(serviceName, serviceVersion));
  }

  @Override
  public void unregister(InstanceId instanceId) {
    if (!instanceTable.containsKey(instanceId)) {
      return;
    }

    ServiceDescriptor service = instanceTable.get(instanceId);
    if (!registryTable.containsKey(service.name)) {
      return;
    }

    if (!registryTable.get(service.name).containsKey(service.version)) {
      return;
    }

    registryTable.get(service.name).get(service.version).remove(instanceId);
    instanceTable.remove(instanceId);
  }

  @Override
  public Set<InstanceId> query(String serviceName, String serviceVersion) {
    Set<InstanceId> instanceSet = new HashSet<>();
    
    if (!registryTable.containsKey(serviceName)) {
      return instanceSet;
    }

    if (serviceVersion == WILDCARD) {
      registryTable.get(serviceName)
        .entrySet()
        .stream()
        .forEach(entry -> instanceSet.addAll(entry.getValue()));

      return instanceSet;
    }

    if (!registryTable.get(serviceName).containsKey(serviceVersion)) {
      return instanceSet;
    }

    instanceSet.addAll(registryTable.get(serviceName).get(serviceVersion));
    return instanceSet;
  }
}
