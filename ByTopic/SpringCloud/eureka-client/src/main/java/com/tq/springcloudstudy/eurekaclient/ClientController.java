package com.tq.springcloudstudy.eurekaclient;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ClientController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/client")
    public String callHello() {
        return restTemplate.getForObject("http://hello-service/hello", String.class);
    }
}
