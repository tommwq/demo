package com.example.manager;

import com.example.domain.Account;
import com.example.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountManager {

    Logger logger = LoggerFactory.getLogger(AccountManager.class);

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserManager userManager;

    @Autowired
    HttpSession httpSession;

    public List<Account> listAccount(Long user) {
        return accountRepository.findAllAccountsByUser(user).stream().map(e -> fromEntity(e)).collect(Collectors.toList());
    }

    public Long open(Long userId) {
        return syncRepository(new Account(null, userId, 0.0)).id;
    }

    public void save(Long userId, Long accountId, Double money) {
        syncRepository(getAccount(accountId).save(money));
    }

    public void withdraw(Long userId, Long accountId, Double money) {
        syncRepository(getAccount(accountId).withdraw(money));
    }

    public Double balance(Long userId, Long accountId) {
        return getAccount(accountId).getBalance();
    }

    private com.example.repository.Account syncRepository(Account account) {
        accountRepository.save(toEntity(account));
    }

    protected Account getAccount(Long accountId) {
        return fromEntity(accountRepository.findById(accountId).get());
    }

    private com.example.repository.Account toEntity(Account account) {
        com.example.repository.Account entity = new com.example.repository.Account();
        entity.id = account.getId();
        entity.user = account.getUser().getId();
        entity.balance = account.getBalance();
        return entity;
    }

    private Account fromEntity(com.example.repository.Account entity) {
        return new Account(entity.id, userManager.getUser(entity.user), entity.balance);
    }
}
