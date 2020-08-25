package com.example.hystrixdemo;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("hello-service")
public interface HelloClient {
    @HystrixCommand
    @GetMapping("/hello")
    public String hello();

    @GetMapping("/nil")
    public String nil();

    @HystrixCommand(defaultFallback = "defaultNil")
    @GetMapping("/nil")
    public String safeNil();

    default public String defaultNil() {
        return "nil";
    }
}
