package com.tq.microservice.instanceregisterservice;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

public class InstanceRegisterServiceApp {
    private static final Logger logger = Logger.getLogger(InstanceRegisterServiceApp.class.getName());
    private Server server;
    
    public static void main(String[] args) {
        try {
            final InstanceRegisterServiceApp app = new InstanceRegisterServiceApp();
            app.start();
            app.blockUntilShutdown();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
            .addService(new InstanceRegisterService())
            .build()
            .start();
        logger.info("started");
        Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    InstanceRegisterServiceApp.this.stop();
                    System.err.println("shutdown");
                }
            });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}





