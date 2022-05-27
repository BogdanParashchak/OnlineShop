package com.parashchak.onlineshop.web.util;

//import jakarta.servlet.http.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

public class ResponseUtil {

    public static void setCookie(HttpServletResponse response, String value, Properties configProperties) {
        Cookie cookie = new Cookie("user-token", value);
        long timeToLive = Long.parseLong(configProperties.getProperty("session.timeToLive"));
        cookie.setMaxAge((int) timeToLive);
        response.addCookie(cookie);
    }
}