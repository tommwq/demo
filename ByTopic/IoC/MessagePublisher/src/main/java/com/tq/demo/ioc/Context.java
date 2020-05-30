package com.tq.demo.ioc;

public class Context {

    public MessagePublisher getPublisher() {
        if (System.console() == null) {
            return new WindowPublisher();
        } else {
            return new ConsolePublisher();
        }
    }
}
