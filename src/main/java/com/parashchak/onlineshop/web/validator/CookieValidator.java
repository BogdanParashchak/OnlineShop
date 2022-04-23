package com.parashchak.onlineshop.web.validator;

import jakarta.servlet.http.*;
import lombok.SneakyThrows;

import java.util.List;

public class CookieValidator {

    @SneakyThrows
    public static void validateCookie(HttpServletRequest request,
                                      HttpServletResponse response,
                                      List<String> sessionList) {
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

        if (!isValid) {
            response.sendRedirect("/login");
        }
    }
}