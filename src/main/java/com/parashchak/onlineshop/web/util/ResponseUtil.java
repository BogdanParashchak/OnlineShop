package com.parashchak.onlineshop.web.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class ResponseUtil {
    public static void setCookie(HttpServletResponse response, String value) {
        Cookie cookie = new Cookie("user-token", value);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }
}