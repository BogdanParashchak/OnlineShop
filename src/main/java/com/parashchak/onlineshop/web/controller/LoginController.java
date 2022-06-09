package com.parashchak.onlineshop.web.controller;

import com.parashchak.onlineshop.security.SecurityService;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Optional;

@Controller
public class LoginController {

    private final PageGenerator pageGenerator;
    private final SecurityService securityService;

    @Autowired
    public LoginController(PageGenerator pageGenerator, SecurityService securityService) {
        this.pageGenerator = pageGenerator;
        this.securityService = securityService;
    }

    @GetMapping("/login")
    @ResponseBody
    public String getLoginPage() {
        return pageGenerator.getPage("loginPage.html");
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