package com.example.controller;

import com.example.manager.AccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class Open {

    private static final Logger logger = LoggerFactory.getLogger(Open.class);

    @Autowired
    HttpSession httpSession;

    @Autowired
    AccountManager accountManager;

    @RequestMapping("/account/open")
    Response<OpenResult> open() {
        try {
            Long user = (Long) httpSession.getAttribute(SessionAttribute.USERID);
            Long account = accountManager.open(user);
            OpenResult result = new OpenResult();
            result.account = account;
            logger.debug("open account {}", account);
            return new Response<>(ReturnCode.OK, result);
        } catch (Exception e) {
            return new Response<>(ReturnCode.OPEN_ACCOUNT_FAILED, null);
        }
    }

    public static final class OpenResult {
        public Long account;
    }
}
