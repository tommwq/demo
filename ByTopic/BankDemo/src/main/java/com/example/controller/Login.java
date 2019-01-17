package com.example.controller;

import java.io.Serializable;

import com.example.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

@RestController
public class Login {

    private static final Logger logger = LoggerFactory.getLogger(Login.class);
    
    public final static class LoginRequest {
        public String username;
        public String password;
    }

    private final static class LoginResult implements Serializable {
    }

    @Autowired
    UserManager userManager;

    @RequestMapping("/login")
    Response<LoginResult> login(@RequestBody LoginRequest loginRequest) {
        int returnCode = ReturnCode.LOGIN_FAILED;
        if (userManager.authorizeUser(loginRequest.username, loginRequest.password)) {
            returnCode = ReturnCode.OK;
        }

        Response<LoginResult> response = new Response(returnCode, null);
        return response;
    }
}
