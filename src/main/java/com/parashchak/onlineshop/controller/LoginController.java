package com.parashchak.onlineshop.controller;

import com.parashchak.onlineshop.security.SecurityService;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.parashchak.onlineshop.web.util.ResponseUtil.setCookie;

@Controller
public class LoginController {

    private final PageGenerator pageGenerator;

    @Autowired
    private SecurityService securityService;

    @Autowired
    public LoginController(PageGenerator pageGenerator) {
        this.pageGenerator = pageGenerator;
    }

    @GetMapping(path = "/login")
    @ResponseBody
    public String getLoginPage() {
        return pageGenerator.getPage("loginPage.html");
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