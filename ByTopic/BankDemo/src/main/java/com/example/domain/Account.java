package com.example.domain;

public class Account {
    private Long id;
    private User user;
    private Double balance;

    public Account(Long id, User user, Double balance) {
        this.id = id;
        this.user = user;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Double getBalance() {
        return balance;
    }
}
