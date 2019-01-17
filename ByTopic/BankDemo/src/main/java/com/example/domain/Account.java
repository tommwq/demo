package com.example.domain;

public class Account {
    private Long id;
    private User user;
    private Double balance;
    private boolean closed = false;

    public Account(Long id, User user, Double balance, boolean closed) {
        this.id = id;
        this.user = user;
        this.balance = balance;
        this.closed = closed;
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

    public Account save(Double money) {
        if (money <= 0) {
            throw new IllegalArgumentException();
        }

        this.balance += money;
        return this;
    }

    public Account withdraw(Double money) {
        if (money <= 0) {
            throw new IllegalArgumentException();
        }

        this.balance -= money;
        return this;
    }

    public boolean isClosed() {
        return closed;
    }

    public Account close() {
        if (balance != 0) {
            throw new IllegalArgumentException();
        }

        closed = true;
        return this;
    }
}
