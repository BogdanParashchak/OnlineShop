package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.User;
import com.parashchak.onlineshop.service.*;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import jakarta.servlet.http.*;
import lombok.*;

import java.io.IOException;

@AllArgsConstructor
public class LoginServlet extends HttpServlet {

    private final PageGenerator pageGenerator = PageGenerator.instance();
    private final UserService userService;
    private final SecurityService securityService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String page = pageGenerator.getPage("loginPage.html");
        response.getWriter().write(page);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User user = userService.get(login);
        if (user != null && securityService.verifyPassword(password, user.getSalt(), user.getPassword())) {
            securityService.createSession(response);
        }
        response.sendRedirect("/products");
    }
}