package com.example.hystrixdemo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyHystrixCommand extends HystrixCommand<String> {

    @Autowired
    HelloClient helloClient;

    public MyHystrixCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("MyGroup"));
    }

    @Override
    protected String run() throws Exception {
        //return helloClient.hello();
        return helloClient.nil();
    }

    @Override
    protected String getFallback() {
        return "fallback";
    }
}
