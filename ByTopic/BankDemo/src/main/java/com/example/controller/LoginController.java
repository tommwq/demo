package com.example.controller;

import java.io.Serializable;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    public final static class LoginRequest {
        public String username;
        public String password;
    }

    public final static class LoginResult implements Serializable {
        public String status;
    }

    public final static class Response<T> implements Serializable {
        protected Response() {}

        public Response(int errorCode, T result) {
            this.result = result;
            this.errorCode = errorCode;
        }
            
        public T result;
        public int errorCode;
    }

    @RequestMapping("/login")
    Response<LoginResult> login(@RequestBody LoginRequest loginRequest) {
        logger.debug("login request", loginRequest);
        LoginResult result = new LoginResult();
        result.status = "ok";
        Response<LoginResult> response = new Response(0, result);
        logger.debug("login response", response);
        return response;
    }
}
