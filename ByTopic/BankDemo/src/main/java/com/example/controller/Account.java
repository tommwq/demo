package com.example.controller;

import com.example.manager.AccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Account {

    private static Logger logger = LoggerFactory.getLogger(Account.class);
    @Autowired
    HttpSession httpSession;
    @Autowired
    AccountManager accountManager;

    @RequestMapping("/account")
    Response<AccountRequest> account() {
        Long user = (Long) httpSession.getAttribute(SessionAttribute.USERID);

        logger.debug("get user id {} from session {}.", user, httpSession.getId());

        if (user == null) {
            return new Response<>(ReturnCode.NOT_LOGGED_IN, null);
        }

        AccountResult result = new AccountResult();
        result.accounts = new ArrayList<>();
        for (com.example.domain.Account account : accountManager.listAccount(user)) {
            AccountResult.AccountItem item = new AccountResult.AccountItem();
            item.id = account.getId();
            item.balance = account.getBalance();
            result.accounts.add(item);
        }

        return new Response(ReturnCode.OK, result);
    }

    private static final class AccountRequest {
    }

    public static final class AccountResult {
        public List<AccountItem> accounts;

        public static final class AccountItem {
            public Long id;
            public Double balance;
        }
    }
}
