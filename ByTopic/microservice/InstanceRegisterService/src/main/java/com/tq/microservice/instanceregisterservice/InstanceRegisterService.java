package com.tq.microservice.instanceregisterservice;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

import com.tq.microservice.common.InstanceId;
import com.tq.microservice.common.Request;
import com.tq.microservice.common.Response;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.function.Function;
import java.util.logging.Logger;

public class InstanceRegisterService extends InstanceRegisterServiceGrpc.InstanceRegisterServiceImplBase {

    private static final Logger logger = Logger.getLogger(InstanceRegisterService.class.getName());

    @Override
    public void registerInstance(Request request, StreamObserver<Response> outputStream) {
        logger.info("enter register instance");

        Response.Builder responseBuilder = Response.newBuilder();

        Any payload = request.getPayload();
        if (!payload.is(RegisterRequest.class) || !payload.isInitialized()) {
            responseBuilder.setMessage("invalid request");
            responseBuilder.setResult(1);
            Response response = responseBuilder.build();
            outputStream.onNext(response);
            outputStream.onCompleted();
            logger.info("leave register instance: invalid request");
            return;
        }

        RegisterRequest registerRequest;
        try {
            registerRequest = payload.unpack(RegisterRequest.class);            
        } catch (InvalidProtocolBufferException exception) {
            exception.printStackTrace();
            System.exit(-1);
        }

        // InstanceId clientId = request.getClientId();
        // String serviceName = request.getServiceName();
        // InstanceId serviceId = request.getServiceId();
        responseBuilder.setMessage("ok");
        responseBuilder.setResult(0);
        outputStream.onNext(responseBuilder.build());
        outputStream.onCompleted();
        logger.info("leave register instance: ok");
    }
}

