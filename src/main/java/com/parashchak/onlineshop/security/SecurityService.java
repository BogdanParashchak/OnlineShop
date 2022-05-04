package com.parashchak.onlineshop.security;

import com.parashchak.onlineshop.entity.User;
import com.parashchak.onlineshop.service.UserService;
import lombok.*;

import java.util.*;

@RequiredArgsConstructor
public class SecurityService {

    @Getter
    private final PasswordHandler passwordHandler = new PasswordHandler();
    @Getter
    private final List<String> sessionList = Collections.synchronizedList(new ArrayList<>());
    private final UserService userService;

    public boolean validateUserToken(Optional<String> userToken) {
        return userToken.isPresent() && sessionList.contains(userToken.get());
    }

    public boolean validateCredentials(String login, String password) {
        Optional<User> optionalUser = userService.get(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return passwordHandler.verifyPassword(password, user.getSalt(), user.getPassword());
        }
        return false;
    }

    public String createToken() {
        String uuid = UUID.randomUUID().toString();
        sessionList.add(uuid);
        return uuid;
    }
}