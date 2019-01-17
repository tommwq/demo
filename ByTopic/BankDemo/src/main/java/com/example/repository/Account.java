package com.example.repository;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public Long user;

    @Column(nullable = false)
    public Double balance;

    @Column(nullable = false)
    public Boolean closed;
}
