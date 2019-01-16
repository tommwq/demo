package com.example.manager;

import com.example.domain.Account;
import com.example.domain.User;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountManager {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserManager userManager;

    @Autowired
    HttpSession httpSession;

    public List<Account> listAccount(Long user) {
        List<Account> accounts = new ArrayList<>();
        for (com.example.repository.Account entity : accountRepository.findAllAccountsByUser(user)) {
            accounts.add(new Account(entity.id, userManager.getUser(entity.user), entity.balance));
        }

        return accounts;
    }
}
