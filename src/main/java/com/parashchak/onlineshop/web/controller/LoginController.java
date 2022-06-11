package com.parashchak.onlineshop.web.controller;

import com.parashchak.onlineshop.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class LoginController {

    private final SecurityService securityService;

    @Autowired
    public LoginController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "loginPage";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userName,
                        @RequestParam String password,
                        HttpServletResponse response) throws IOException {

        Optional<AbstractMap.SimpleEntry<String, Long>> cookieData = securityService.login(userName, password);
        if (cookieData.isPresent()) {
            AbstractMap.SimpleEntry<String, Long> entry = cookieData.get();
            Cookie cookie = new Cookie("user-token", entry.getKey());
            cookie.setMaxAge((int) entry.getValue().longValue());
            response.addCookie(cookie);
        }
        return "redirect:/";
    }
}