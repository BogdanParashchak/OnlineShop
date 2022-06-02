package com.parashchak.onlineshop.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

public class ResponseUtil {

    public static void setCookie(HttpServletResponse response, String value, long timeToLive) {
        Cookie cookie = new Cookie("user-token", value);
        cookie.setMaxAge((int) timeToLive);
        response.addCookie(cookie);
    }
}