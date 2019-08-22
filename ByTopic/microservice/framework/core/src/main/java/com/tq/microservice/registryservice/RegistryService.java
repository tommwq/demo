package com.tq.microservice.registryservice;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
//import com.tq.microservice.common.Error;
//import com.tq.microservice.common.InstanceId;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RegistryService extends RegistryServiceGrpc.RegistryServiceImplBase {

    // private static class InstanceDescriptor {
    //     public int ip = 0;
    //     public int port = 0;
    //     public int startTime = 0;
    //     public int pid = 0;
    //     public InstanceType type = InstanceType.CLIENT;
    //     public String serviceName = "";
    //     public String serviceVersion = "";
    // }

    // // tag = "ip:port"
    // private static ConcurrentHashMap<String,List<InstanceDescriptor>> instanceTable = new ConcurrentHashMap<>();

    // // 注册
    // // 查询

    // private static final Logger logger = Logger.getLogger(RegistryService.class.getName());

    // // private String generateTag(InstanceId instanceId) {
    // //     return String.format("%d:%d", instanceId.getIp(), instanceId.getPort());
    // // }

    // @Override
    // public void registerInstance(RegisterInstanceRequest request, StreamObserver<RegisterInstanceResponse> outputStream) {

    //     // InstanceType instanceType = request.getType();
    //     // InstanceId instanceId = request.getInstanceId();
    //     // String serviceName = request.getServiceName();
    //     // String serviceVersion = request.getServiceVersion();

    //     // InstanceDescriptor descriptor = new InstanceDescriptor();
    //     // descriptor.ip = instanceId.getIp();
    //     // descriptor.port = instanceId.getPort();
    //     // descriptor.startTime = instanceId.getStartTime();
    //     // descriptor.pid = instanceId.getPid();
    //     // descriptor.type = instanceType;
    //     // descriptor.serviceName = serviceName;
    //     // descriptor.serviceVersion = serviceVersion;

    //     // logger.info("service name: " + descriptor.serviceName);

    //     // String tag = generateTag(instanceId);
    //     // if (!instanceTable.containsKey(tag)) {
    //     //     instanceTable.put(tag, new ArrayList<>());
    //     // }
    //     // instanceTable.get(tag).add(descriptor);

    //     // outputStream.onNext(RegisterInstanceResponse.newBuilder().build());
    //     // outputStream.onCompleted();
    // }

    // @Override
    // public void queryService(QueryServiceRequest request, StreamObserver<QueryServiceResponse> outputStream) {

    //     // logger.info("process thread: " + Thread.currentThread().getId() + " " + Thread.currentThread().getName());

    //     // final String serviceName = request.getServiceName();
    //     // final String serviceVersion = request.getServiceVersion();

    //     // List<InstanceId> serviceInstances = instanceTable
    //     //         .values()
    //     //         .stream()
    //     //         .flatMap(Collection::stream)
    //     //         .filter((descriptor) -> descriptor.serviceName.equals(serviceName) && descriptor.serviceVersion.equals(serviceVersion))
    //     //         .map((descriptor)-> InstanceId.newBuilder()
    //     //                 .setIp(descriptor.ip)
    //     //                 .setPort(descriptor.port)
    //     //                 .setStartTime(descriptor.startTime)
    //     //                 .setPid(descriptor.pid)
    //     //                 .build())
    //     //         .collect(Collectors.toList());

    //     // QueryServiceResponse response = QueryServiceResponse.newBuilder()
    //     //         .setServiceName(serviceName)
    //     //         .setServiceVersion(serviceVersion)
    //     //         .addAllInstances(serviceInstances)
    //     //         .build();

    //     // outputStream.onNext(response);
    //     // outputStream.onCompleted();
    // }

    // @Override
    // public void queryServiceCatalog(QueryServiceCatalogRequest request, StreamObserver<QueryServiceCatalogResponse> outputStream) {

    //     // logger.info("process thread: " + Thread.currentThread().getId() + " " + Thread.currentThread().getName());

    //     // List<String> serviceNames = instanceTable
    //     //         .values()
    //     //         .stream()
    //     //         .flatMap(Collection::stream)
    //     //         .map((descriptor) -> descriptor.serviceName)
    //     //         .distinct()
    //     //         .collect(Collectors.toList());

    //     // QueryServiceCatalogResponse response = QueryServiceCatalogResponse.newBuilder()
    //     //         .addAllServiceNames(serviceNames)
    //     //         .build();

    //     // outputStream.onNext(response);
    //     // outputStream.onCompleted();
    // }
}

