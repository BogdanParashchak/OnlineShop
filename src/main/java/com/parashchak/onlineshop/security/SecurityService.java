package com.parashchak.onlineshop.security;

import com.parashchak.onlineshop.entity.User;
import com.parashchak.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SecurityService {

    private final PasswordHandler passwordHandler = new PasswordHandler();
    private final List<Session> sessionList = Collections.synchronizedList(new ArrayList<>());

    private final UserService userService;
    private final long timeToLive;

    @Autowired
    public SecurityService(UserService userService, @Value("${session.timeToLive}") String timeToLive) {
        this.userService = userService;
        this.timeToLive = Long.parseLong(timeToLive);
    }


    public boolean validateUserToken(String userToken) {
        LocalDateTime actualDateTime = LocalDateTime.now();

        synchronized (sessionList) {
            ListIterator<Session> iterator = sessionList.listIterator(sessionList.size());

            while (iterator.hasPrevious()) {
                Session session = iterator.previous();

                if (session.getUserToken().equals(userToken)) {
                    if (session.getExpireDateTime().isAfter(actualDateTime)) {
                        return true;
                    } else {
                        iterator.remove();
                        break;
                    }
                }
            }

            return false;
        }
    }

    public Optional<AbstractMap.SimpleEntry<String, Long>> login(String userName, String password) {
        if (validateCredentials(userName, password)) {
            String userToken = UUID.randomUUID().toString();
            LocalDateTime expireDateTime = LocalDateTime.now().plusSeconds(timeToLive);
            Session session = new Session(userToken, expireDateTime);
            sessionList.add(session);
            AbstractMap.SimpleEntry<String, Long> cookieData = new AbstractMap.SimpleEntry<>(userToken, timeToLive);
            return Optional.of(cookieData);
        }
        return Optional.empty();
    }

    private boolean validateCredentials(String userName, String password) {
        Optional<User> optionalUser = userService.findByUserName(userName);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return passwordHandler.verifyPassword(password, user.getSalt(), user.getPassword());
        }
        return false;
    }
}