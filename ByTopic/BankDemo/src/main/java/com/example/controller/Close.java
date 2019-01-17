package com.example.controller;

import com.example.manager.AccountManager;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class Close {

    @Autowired
    HttpSession httpSession;

    @Autowired
    AccountManager accountManager;

    private static final class CloseRequest {
        public Long account;
    }

    @RequestMapping("/{account}/close")
    Response close(@PathVariable Long account, @RequestBody CloseRequest closeRequest) {
        try {
            Long user = (Long) httpSession.getAttribute(SessionAttribute.USERID);
            accountManager.close(user, account);
            return new Response(ReturnCode.OK, null);
        } catch (Exception e) {
            return new Response(ReturnCode.CLOSE_ACCOUNT_FAILED, null);
        }
    }
}
