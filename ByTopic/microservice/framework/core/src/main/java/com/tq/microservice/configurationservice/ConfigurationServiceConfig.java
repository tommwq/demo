package com.tq.microservice.configurationservice;

public class ConfigurationServiceConfig {
    private int port;

    public int getPort() {
        return port;
    }

    public ConfigurationServiceConfig setPort(int port) {
        this.port = port;
        return this;
    }
}
