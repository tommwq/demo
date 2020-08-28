package com.tq.springcloudstudy.firstrestfulapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ConfigurationProperties(prefix="my.env")
@Component
class MyEnv {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

@Component
@Endpoint(id="user")
class UserEndpoint {
    @ReadOperation
    public List<Map<String,Object>> health() {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("name", "John");
        list.add(map);

        return list;
    }
}


@RestController
public class HelloController {

    @Autowired
    Environment environment;

    @GetMapping("/hello")
    public String hello() {
        //return environment.getProperty("server.port");
        //return port;
        return myEnv.getMessage();
    }

    @Value("${server.port}")
    private String port;

    @Autowired
    MyEnv myEnv;
}
