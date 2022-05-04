package com.parashchak.onlineshop.web.util;

import jakarta.servlet.http.*;

import java.util.Optional;

public class RequestUtil {
    public static Optional<String> getUserToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName())) {
                    return Optional.of(cookie.getValue());
                }
            }
        }
        return Optional.empty();
    }
}