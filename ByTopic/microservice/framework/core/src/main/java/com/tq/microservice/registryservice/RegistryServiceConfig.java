package com.tq.microservice.registryservice;

public class RegistryServiceConfig {
    private int port;

    public int getPort() {
        return port;
    }

    public RegistryServiceConfig setPort(int port) {
        this.port = port;
        return this;
    }
}
