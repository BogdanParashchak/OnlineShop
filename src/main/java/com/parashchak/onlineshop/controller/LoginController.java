package com.parashchak.onlineshop.controller;

import com.parashchak.onlineshop.security.SecurityService;
import com.parashchak.templater.Templater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.parashchak.onlineshop.web.util.ResponseUtil.setCookie;

@Controller
public class LoginController {

    @Autowired
    private Templater templater;

    @Autowired
    private SecurityService securityService;

    @GetMapping(path = "/login")
    @ResponseBody
    public String getLoginPage() {
        return templater.getPage("loginPage.html");
    }

    @PostMapping(path = "/login")
    public String login(@RequestParam String login,
                       @RequestParam String password,
                       HttpServletResponse response) throws IOException {

        if (securityService.validateCredentials(login, password)) {
            String token = securityService.login();
            setCookie(response, token, securityService.getTimeToLive());
        }
        return "redirect:/";
    }
}