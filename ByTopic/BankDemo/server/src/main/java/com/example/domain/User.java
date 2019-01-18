package com.example.domain;

public class User {
    private String username;
    private String password;
    private Long id;

    public User(com.example.repository.User entity) {

        if (entity == null) {
            throw new UserNotFoundException();
        }

        username = entity.username;
        password = entity.password;
        id = entity.id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public boolean isPasswordMatch(String password) {
        return this.password.equals(password);
    }
}
