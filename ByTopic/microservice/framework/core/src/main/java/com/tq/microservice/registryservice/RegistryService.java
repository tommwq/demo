package com.tq.microservice.registryservice;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
//import com.tq.microservice.common.InstanceId;
import com.tq.microservice.common.InstanceLocalId;
import com.tq.microservice.common.InstanceLocation;
import com.tq.microservice.registryservice.adapter.InMemoryRegistryServiceAdapter;
import com.tq.microservice.registryservice.adapter.InstanceId;
import com.tq.microservice.registryservice.adapter.LocalId;
import com.tq.microservice.registryservice.adapter.Location;
import com.tq.microservice.registryservice.adapter.RegistryServiceAdapter;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * registry service.
 */
public class RegistryService extends RegistryServiceGrpc.RegistryServiceImplBase {

  private static final Map<String,Class> adapterTable = new HashMap<>();
  static {
    adapterTable.put("IN_MEMORY", InMemoryRegistryServiceAdapter.class);
  }

  private RegistryServiceAdapter adapter;

  public RegistryService(String adapterName) {
    try {
      adapter = (RegistryServiceAdapter) adapterTable.get(adapterName).newInstance();
    } catch (InstantiationException e) {
      throw new RuntimeException("cannot create registry service adapter", e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException("cannot create registry service adapter", e);
    }
  }

  @Override
  public void registerInstance(RegisterInstanceRequest input, StreamObserver<RegisterInstanceResponse> outputStream) {

    String serviceName = input.getServiceName();
    String serviceVersion = input.getServiceVersion();
    String host = input.getInstanceId().getLocation().getHost();
    int port = input.getInstanceId().getLocation().getPort();
    int time = input.getInstanceId().getLocalId().getStartTime();
    int pid = input.getInstanceId().getLocalId().getPid();
    
    adapter.register(serviceName, serviceVersion, new InstanceId(new Location(host, port), new LocalId(time, pid)));
    
    outputStream.onNext(RegisterInstanceResponse.newBuilder().build());
    outputStream.onCompleted();
  }

  @Override
  public void queryService(QueryServiceRequest input, StreamObserver<QueryServiceResponse> outputStream) {

    String serviceName = input.getServiceName();
    String serviceVersion = input.getServiceVersion();

    List<com.tq.microservice.common.InstanceId> instanceList = adapter.query(serviceName, serviceVersion)
      .stream()
      .map(instance -> com.tq.microservice.common.InstanceId.newBuilder()
           .setLocation(com.tq.microservice.common.InstanceLocation.newBuilder()
                        .setHost(instance.location().address())
                        .setPort(instance.location().port())
                        .build())
           .setLocalId(com.tq.microservice.common.InstanceLocalId.newBuilder()
                       .setStartTime(instance.localId().startTime())
                       .setPid(instance.localId().pid())
                       .build())
           .build())
      .collect(Collectors.toList());

    QueryServiceResponse response = QueryServiceResponse.newBuilder()
      .setServiceName(serviceName)
      .setServiceVersion(serviceVersion)
      .addAllInstance(instanceList)
      .build();

    outputStream.onNext(response);
    outputStream.onCompleted();
  }

  @Override
  public void queryServiceCatalog(QueryServiceCatalogRequest input, StreamObserver<QueryServiceCatalogResponse> outputStream) {

    // List<String> serviceNames = instanceTable
    //   .values()
    //   .stream()
    //   .flatMap(Collection::stream)
    //   .map((descriptor) -> descriptor.serviceName)
    //   .distinct()
    //   .collect(Collectors.toList());

    QueryServiceCatalogResponse response = QueryServiceCatalogResponse.newBuilder()
      // .addAllServiceNames(serviceNames)
      .build();

    outputStream.onNext(response);
    outputStream.onCompleted();
  }
}

