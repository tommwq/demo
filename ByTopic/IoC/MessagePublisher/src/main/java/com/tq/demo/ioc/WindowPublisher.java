package com.tq.demo.ioc;

import javax.swing.JOptionPane;

public class WindowPublisher implements MessagePublisher {
    public void publish(String message) {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }
}

