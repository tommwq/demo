package com.example.manager;

import com.example.controller.SessionAttribute;
import com.example.domain.User;
import com.example.domain.UserNotFoundException;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
public class UserManager {
    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpSession httpSession;

    public boolean authorizeUser(String username, String password) {
        Optional<com.example.repository.User> entity = userRepository.lookupUser(username);
        if (!entity.isPresent()) {
            return false;
        }

        User user = new User(entity.get());
        boolean authorized = user.isPasswordMatch(password);
        if (authorized) {
            httpSession.setAttribute(SessionAttribute.USERNAME, username);
            httpSession.setAttribute(SessionAttribute.USERID, user.getId());
        }
        return authorized;
    }

    public User getUser(Long userId) {
        return new User(userRepository.findById(userId).get());
    }
}
