package com.tq.microservice.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.protobuf.Any;

import com.tq.microservice.common.Request;
import com.tq.microservice.common.Response;
import com.tq.microservice.instanceregisterservice.RegisterRequest;
import com.tq.microservice.instanceregisterservice.RegisterResponse;
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

    public void greet(String name) {
        RegisterRequest payload = RegisterRequest.newBuilder()
            .setServiceName("client")
            .build();
        
        Request request = Request.newBuilder()
            .setPayload(Any.pack(payload))
            .build();

        //        try {
            Response response = instanceRegisterService.registerInstance(request);
            logger.info("ok" + response);
        // } catch (Exception e) {
        //     logger.info(e.getMessage());
        // }
    }
    
    public static void main(String[] args) {
        final TestClientApp app = new TestClientApp("localhost", 50051);
        try {
            app.greet("world");
            app.shutdown();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }  
}
