package com.example.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Account implements Serializable {
    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false)
    public Long user;

    @Column(nullable = false)
    public Double balance;
}
