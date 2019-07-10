package com.tq.microservice.instanceregisterservice;

public class InstanceRegisterServiceApp {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        System.out.println(new InstanceRegisterServiceApp().getGreeting());
    }
}
