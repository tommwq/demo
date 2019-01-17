package com.example.controller;

import com.example.manager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
public class Login {

    private static final Logger logger = LoggerFactory.getLogger(Login.class);
    @Autowired
    UserManager userManager;

    @RequestMapping("/login")
    Response<LoginResult> login(@RequestBody LoginRequest loginRequest) {
        int returnCode = ReturnCode.LOGIN_FAILED;
        try {
            if (userManager.authorizeUser(loginRequest.username, loginRequest.password)) {
                returnCode = ReturnCode.OK;
            }
        } catch (Exception e) {
            logger.error("login error", e);
        }
        Response<LoginResult> response = new Response(returnCode, null);
        return response;
    }

    public final static class LoginRequest {
        public String username;
        public String password;
    }

    private final static class LoginResult implements Serializable {
    }
}
