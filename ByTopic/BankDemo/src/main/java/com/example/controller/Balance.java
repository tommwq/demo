package com.example.controller;

import com.example.manager.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

@RestController
public class Balance {

    @Autowired
    HttpSession httpSession;
    @Autowired
    AccountManager accountManager;

    @RequestMapping("/{account}/balance")
    Response<BalanceResult> balance(@PathVariable Long account, @RequestBody BalanceRequest balanceRequest) {
        try {
            Long user = (Long) httpSession.getAttribute(SessionAttribute.USERID);
            if (user == null) {
                return new Response<>(ReturnCode.NOT_LOGGED_IN, null);
            }

            BalanceResult result = new BalanceResult();
            result.balance = accountManager.balance(user, balanceRequest.account);

            return new Response<>(ReturnCode.OK, result);
        } catch (Exception e) {
            return new Response<>(ReturnCode.GET_BALANCE_FAILED, null);
        }
    }

    public static final class BalanceRequest implements Serializable {
        public Long account;
    }

    public static final class BalanceResult implements Serializable {
        public Double balance;
    }
}
