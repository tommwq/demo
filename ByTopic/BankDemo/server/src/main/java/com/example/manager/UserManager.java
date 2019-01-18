package com.example.manager;

import com.example.controller.SessionAttribute;
import com.example.domain.User;
import com.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class UserManager {

    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpSession httpSession;

    public boolean authorizeUser(String username, String password) {
        logger.debug("authorizeUser");
        User user = new User(userRepository.lookupUser(username).get());
        boolean authorized = user.isPasswordMatch(password);
        if (authorized) {
            httpSession.setAttribute(SessionAttribute.USERNAME, username);
            httpSession.setAttribute(SessionAttribute.USERID, user.getId());
            final int TEN_MINUTE = 10 * 60;
            httpSession.setMaxInactiveInterval(TEN_MINUTE);
            logger.debug("verified");
        }
        return authorized;
    }

    public User getUser(Long userId) {
        return new User(userRepository.findById(userId).get());
    }
}
