package com.example.manager;

import com.example.domain.Account;
import com.example.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
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

    public void save(Long userId, Long accountId, Double money) {
        Optional<com.example.repository.Account> entity = accountRepository.findById(accountId);
        if (!entity.isPresent()) {
            throw new IllegalAccountException();
        }

        Account account = fromEntity(entity.get());
        account.save(money);

        syncRepository(account);
    }

    public void withdraw(Long userId, Long accountId, Double money) {
        Optional<com.example.repository.Account> entity = accountRepository.findById(accountId);
        if (!entity.isPresent()) {
            throw new IllegalAccountException();
        }

        Account account = fromEntity(entity.get());
        account.withdraw(money);

        syncRepository(account);
    }

    private void syncRepository(Account account) {
        logger.debug("save account {} {} {}", account.getId(), account.getUser().getId(), account.getBalance());
        accountRepository.save(toEntity(account));
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
