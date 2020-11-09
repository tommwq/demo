package com.example.hystrixdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallHelloController {
    @Autowired
    MyHystrixCommand command;

    @Autowired
    HelloClient helloClient;

    @GetMapping("/callhello1")
    public String callHello1() {
        return command.execute();
    }

    @GetMapping("/callhello2")
    public String callHello2() {
        return helloClient.hello();
    }

    @GetMapping("/callhello3")
    public String callHello3() {
        return helloClient.safeNil();
    }
}
