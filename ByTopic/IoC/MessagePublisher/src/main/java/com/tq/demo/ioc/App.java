package com.tq.demo.ioc;

import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) {

        Context context = new Context();
        MessagePublisher publisher = context.getPublisher();
        
        String message = "Hello, world!";
        publisher.publish(message);

        System.exit(0);
    }
}
