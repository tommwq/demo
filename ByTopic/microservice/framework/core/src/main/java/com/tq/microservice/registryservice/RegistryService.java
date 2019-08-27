package com.tq.microservice.registryservice;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.tq.microservice.common.InstanceId;
import com.tq.microservice.common.InstanceLocalId;
import com.tq.microservice.common.InstanceLocation;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * registry service.
 */
public class RegistryService extends RegistryServiceGrpc.RegistryServiceImplBase {

  private static class InstanceDescriptor {
    public int ip = 0;
    public int port = 0;
    public int startTime = 0;
    public int pid = 0;
    public InstanceType type = InstanceType.CLIENT;
    public String serviceName = "";
    public String serviceVersion = "";
  }

  private static ConcurrentHashMap<String,List<InstanceDescriptor>> instanceTable = new ConcurrentHashMap<>();

  @Override
  public void registerInstance(RegisterInstanceRequest request, StreamObserver<RegisterInstanceResponse> outputStream) {

    // InstanceType instanceType = request.getType();
    // InstanceId instanceId = request.getInstanceId();
    // String serviceName = request.getServiceName();
    // String serviceVersion = request.getServiceVersion();

    // InstanceDescriptor descriptor = new InstanceDescriptor();
    // descriptor.ip = instanceId.getIp();
    // descriptor.port = instanceId.getPort();
    // descriptor.startTime = instanceId.getStartTime();
    // descriptor.pid = instanceId.getPid();
    // descriptor.type = instanceType;
    // descriptor.serviceName = serviceName;
    // descriptor.serviceVersion = serviceVersion;

    // String tag = generateTag(instanceId);
    // if (!instanceTable.containsKey(tag)) {
    //   instanceTable.put(tag, new ArrayList<>());
    // }
    // instanceTable.get(tag).add(descriptor);

    outputStream.onNext(RegisterInstanceResponse.newBuilder().build());
    outputStream.onCompleted();
  }

  @Override
  public void queryService(QueryServiceRequest input, StreamObserver<QueryServiceResponse> outputStream) {

    final String serviceName = input.getServiceName();
    final String serviceVersion = input.getServiceVersion();
    final String LOOPBACK_IP = "127.0.0.1";

    List<InstanceId> instanceList = Arrays.asList(InstanceId.newBuilder()
                                                  .setLocation(InstanceLocation.newBuilder()
                                                               .setHost(LOOPBACK_IP)
                                                               .setPort(50052)
                                                               .build())
                                                  .build());

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

    List<String> serviceNames = instanceTable
      .values()
      .stream()
      .flatMap(Collection::stream)
      .map((descriptor) -> descriptor.serviceName)
      .distinct()
      .collect(Collectors.toList());

    QueryServiceCatalogResponse response = QueryServiceCatalogResponse.newBuilder()
      .addAllServiceNames(serviceNames)
      .build();

    outputStream.onNext(response);
    outputStream.onCompleted();
  }
}

