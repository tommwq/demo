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
public class Withdraw {

    private static final Logger logger = LoggerFactory.getLogger(Withdraw.class);
    @Autowired
    HttpSession httpSession;
    @Autowired
    AccountManager accountManager;

    @RequestMapping("/{account}/withdraw")
    Response withdraw(@PathVariable Long account, @RequestBody WithdrawRequest withdrawRequest) {

        Long userId = (Long) httpSession.getAttribute(SessionAttribute.USERID);
        if (userId == null) {
            return new Response(ReturnCode.NOT_LOGGED_IN, null);
        }

        try {
            accountManager.withdraw(userId, withdrawRequest.account, withdrawRequest.money);
            return new Response(ReturnCode.OK, null);
        } catch (Exception e) {
            logger.error("withdraw", e);
            return new Response(ReturnCode.WITHDRAW_FAILED, null);
        }
    }

    public static final class WithdrawRequest implements Serializable {
        public Long account;
        public Double money;
    }
}
