package com.parashchak.onlineshop.service;

import com.parashchak.onlineshop.security.PasswordHandler;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class SecurityService {

    @Getter
    private final PasswordHandler passwordHandler = new PasswordHandler();
    @Getter
    private final List<String> sessionList = new ArrayList<>();

    @SneakyThrows
    public boolean validateSession(HttpServletRequest request) {
        boolean isValid = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName()) && sessionList.contains(cookie.getValue())) {
                    isValid = true;
                    break;
                }
            }
        }
        return isValid;
    }

    public void createSession(HttpServletResponse response) {
        String uuid = UUID.randomUUID().toString();
        sessionList.add(uuid);
        Cookie cookie = new Cookie("user-token", uuid);
        response.addCookie(cookie);
    }

    public boolean verifyPassword(String providedPassword, String salt, String encryptedPassword) {
        return passwordHandler.verifyPassword(providedPassword, salt, encryptedPassword);
    }
}