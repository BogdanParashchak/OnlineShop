package com.parashchak.onlineshop.security;

import com.parashchak.onlineshop.entity.User;
import com.parashchak.onlineshop.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class SecurityService {

    private final PasswordHandler passwordHandler = new PasswordHandler();
    private final List<Session> sessionList = Collections.synchronizedList(new ArrayList<>());
    private final UserService userService;

    @Getter
    private final long timeToLive;

    @Autowired
    public SecurityService(UserService userService, @Value("${session.timeToLive}") String timeToLive) {
        this.userService = userService;
        this.timeToLive = Long.parseLong(timeToLive);
    }


    public boolean validateUserToken(Optional<String> userToken) {
        if (userToken.isPresent()) {
            for (int i = sessionList.size() - 1; i >= 0; --i) {
                Session session = sessionList.get(i);
                if (session.getToken().equals(userToken.get())) {
                    if (session.getExpireDateTime().isAfter(LocalDateTime.now())) {
                        return true;
                    } else {
                        sessionList.remove(i);
                    }
                }
            }
        }
        return false;
    }

    public boolean validateCredentials(String login, String password) {
        Optional<User> optionalUser = userService.get(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return passwordHandler.verifyPassword(password, user.getSalt(), user.getPassword());
        }
        return false;
    }

    public String login() {
        String token = UUID.randomUUID().toString();
        LocalDateTime expireDateTime = LocalDateTime.now().plusSeconds(timeToLive);
        Session session = new Session(token, expireDateTime);
        sessionList.add(session);
        return token;
    }
}