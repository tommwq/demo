package com.tq.microservice.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.protobuf.Any;

import com.tq.microservice.common.InstanceId;
import com.tq.microservice.instanceregisterservice.RegisterInstanceRequest;
import com.tq.microservice.instanceregisterservice.RegisterInstanceResponse;
import com.tq.microservice.instanceregisterservice.QueryServiceRequest;
import com.tq.microservice.instanceregisterservice.QueryServiceResponse;
import com.tq.microservice.instanceregisterservice.QueryServiceCatalogRequest;
import com.tq.microservice.instanceregisterservice.QueryServiceCatalogResponse;
import com.tq.microservice.instanceregisterservice.InstanceRegisterServiceGrpc;

public class TestClientApp {
    private static final Logger logger = Logger.getLogger(TestClientApp.class.getName());

    private final ManagedChannel channel;
    private final InstanceRegisterServiceGrpc.InstanceRegisterServiceBlockingStub instanceRegisterService;

    public TestClientApp(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
             .usePlaintext()
             .build());
    }

    private TestClientApp(ManagedChannel channel) {
        this.channel = channel;
        instanceRegisterService = InstanceRegisterServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void registerService() {
        RegisterInstanceRequest request = RegisterInstanceRequest.newBuilder()
            .setServiceName("client")
            .setServiceVersion("1.0")
            .setInstanceId(InstanceId.newBuilder()
                         .setIp(1234)
                         .setPort(4321)
                         .setPid(123)
                         .setStartTime(321)
                         .build())
            .build();
        
        RegisterInstanceResponse response = instanceRegisterService.registerInstance(request);
        logger.info("response: " + response);
    }

    public void queryService() {
        QueryServiceRequest request = QueryServiceRequest.newBuilder()
            .setServiceName("client")
            .setServiceVersion("1.0")
            .build();
        
        QueryServiceResponse response = instanceRegisterService.queryService(request);
        logger.info("response: " + response);
    }

    public void queryServiceCatalog() {
        QueryServiceCatalogRequest request = QueryServiceCatalogRequest.newBuilder().build();
        QueryServiceCatalogResponse response = instanceRegisterService.queryServiceCatalog(request);
        logger.info("response: " + response);
    }
    
    public static void main(String[] args) {
        final TestClientApp app = new TestClientApp("localhost", 50051);
        try {
            app.registerService();
            app.queryService();
            app.queryServiceCatalog();
            
            app.shutdown();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }  
}
