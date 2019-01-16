package com.example.repository;

import java.io.Serializable;
import javax.persistence.*;

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
