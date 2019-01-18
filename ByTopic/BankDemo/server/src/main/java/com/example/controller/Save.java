package com.example.controller;

import com.example.manager.AccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

@RestController
public class Save {

    private static final Logger logger = LoggerFactory.getLogger(Save.class);

    @Autowired
    AccountManager accountManager;

    @Autowired
    HttpSession httpSession;

    @RequestMapping("/{account}/save")
    Response save(@PathVariable Long account, @RequestBody SaveRequest saveRequest) {
        Long userId = (Long) httpSession.getAttribute(SessionAttribute.USERID);
        if (userId == null) {
            return new Response(ReturnCode.NOT_LOGGED_IN, null);
        }

        try {
            accountManager.save(userId, saveRequest.account, saveRequest.money);
            return new Response(ReturnCode.OK, null);
        } catch (Exception e) {
            return new Response(ReturnCode.SAVE_FAILED, null);
        }
    }

    public static final class SaveRequest implements Serializable {
        public Long account;
        public Double money;
    }
}