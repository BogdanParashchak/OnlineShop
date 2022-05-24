package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.security.SecurityService;
import com.parashchak.onlineshop.service.ServiceLocator;
import com.parashchak.templater.Templater;
import jakarta.servlet.http.*;
import lombok.AllArgsConstructor;

import java.io.IOException;

import static com.parashchak.onlineshop.web.util.ResponseUtil.setCookie;

@AllArgsConstructor
public class LoginServlet extends HttpServlet {

    private final Templater templater = new Templater("templates");
    private final SecurityService securityService = ServiceLocator.getService(SecurityService.class);
    ;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String page = templater.getPage("loginPage.html");
        response.getWriter().write(page);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (securityService.validateCredentials(login, password)) {
            setCookie(response, securityService.createToken());
        }
        response.sendRedirect("/");
    }
}