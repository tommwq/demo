package com.example.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false)
    public String username;

    @Column(nullable = false)
    public String password;
}
