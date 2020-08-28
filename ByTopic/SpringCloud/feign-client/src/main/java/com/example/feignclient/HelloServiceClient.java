package com.example.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value="hello-service")
public interface HelloServiceClient {
    @GetMapping("/hello")
    String hello();
}
