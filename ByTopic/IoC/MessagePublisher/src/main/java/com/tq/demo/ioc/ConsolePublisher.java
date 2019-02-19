package com.tq.demo.ioc;

public class ConsolePublisher implements MessagePublisher {
    public void publish(String message) {
        System.console().printf("%s\n", message).flush();
    }
}
