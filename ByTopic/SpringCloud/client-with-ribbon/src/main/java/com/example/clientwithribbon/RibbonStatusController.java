package com.example.clientwithribbon;


import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RibbonStatusController {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @GetMapping("/ribbonstatus")
    public Object ribbonStatus() {
        ServiceInstance instance = loadBalancer.choose("hello-service");
        return instance;
    }
}
