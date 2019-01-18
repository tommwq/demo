package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class Logout {

    @Autowired
    HttpSession httpSession;

    @RequestMapping("/logout")
    Response<Object> logout() {
        httpSession.invalidate();
        return new Response<>(ReturnCode.OK, null);
    }
}
