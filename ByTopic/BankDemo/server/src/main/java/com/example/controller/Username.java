package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class Username {

    @Autowired
    HttpSession httpSession;

    @RequestMapping("/username")
    Response isLoggedIn() {
        String username = (String) httpSession.getAttribute(SessionAttribute.USERNAME);
        if (username == null) {
            return new Response(ReturnCode.NOT_LOGGED_IN);
        }

        UsernameResult result = new UsernameResult();
        result.username = username;
        return new Response(ReturnCode.OK, result);
    }

    private static final class UsernameResult {
        public String username;
    }
}
