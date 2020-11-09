package com.example.feignclient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallHelloController {
    @Autowired
    HelloServiceClient helloServiceClient;

    @GetMapping("callhello")
    public String callHello() {
        return helloServiceClient.hello();
    }
}
