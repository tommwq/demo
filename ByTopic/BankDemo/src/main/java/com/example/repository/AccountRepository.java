package com.example.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {

    @Query(value = "SELECT id,user,balance FROM account WHERE user=?1", nativeQuery = true)
    List<Account> findAllAccountsByUser(Long user);
}
