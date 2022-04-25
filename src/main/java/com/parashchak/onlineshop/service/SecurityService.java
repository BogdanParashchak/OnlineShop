package com.parashchak.onlineshop.service;

import com.parashchak.onlineshop.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class SecurityService {

    private final List<String> sessionList = new ArrayList<>();

    @SneakyThrows
    public boolean validateSession(HttpServletRequest request) {
        boolean isValid = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName())) {
                    if (sessionList.contains(cookie.getValue())) {
                        isValid = true;
                    }
                    break;
                }
            }
        }
        return isValid;
    }

    public void createSession(User user, HttpServletResponse response) {
        if (user != null) {
            String uuid = UUID.randomUUID().toString();
            sessionList.add(uuid);
            Cookie cookie = new Cookie("user-token", uuid);
            response.addCookie(cookie);
        }
    }
}
