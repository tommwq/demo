package com.example.manager;

import com.example.controller.SessionAttribute;
import com.example.domain.User;
import com.example.domain.UserNotFoundException;
import com.example.repository.Account;
import com.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
public class UserManager {

    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);

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

            logger.debug("save user id {} to session {}.", user.getId(), httpSession.getId());
        }
        return authorized;
    }

    public User getUser(Long userId) {
        Optional<com.example.repository.User> opt = userRepository.findById(userId);
        if (!opt.isPresent()) {
            throw new IllegalArgumentException();
        }

        return new User(opt.get());
    }
}
