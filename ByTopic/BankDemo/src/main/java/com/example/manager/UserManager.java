package com.example.manager;

import com.example.domain.User;
import com.example.domain.UserNotFoundException;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserManager {
    @Autowired
    UserRepository userRepository;

    public boolean authorizeUser(String username, String password) {
        try {
            return new User(userRepository.lookupUser(username)).isPasswordMatch(password);
        } catch (UserNotFoundException e) {
            return false;
        }
    }
}
