package com.parashchak.onlineshop.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;

import java.util.*;

@RequiredArgsConstructor
public class SecurityService {

    @Getter
    private final PasswordHandler passwordHandler = new PasswordHandler();
    @Getter
    private final List<String> sessionList = new ArrayList<>();

    public boolean validateUserToken(Optional<String> userToken) {
        return userToken.isPresent() && sessionList.contains(userToken.get());
    }

    public void createSession(HttpServletResponse response) {
        String uuid = UUID.randomUUID().toString();
        sessionList.add(uuid);
        Cookie cookie = new Cookie("user-token", uuid);
        cookie.setMaxAge(28800);
        response.addCookie(cookie);
    }

    public boolean verifyPassword(String providedPassword, String salt, String encryptedPassword) {
        return passwordHandler.verifyPassword(providedPassword, salt, encryptedPassword);
    }
}