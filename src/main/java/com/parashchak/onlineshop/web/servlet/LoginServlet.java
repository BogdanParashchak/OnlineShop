package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.User;
import com.parashchak.onlineshop.service.*;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import jakarta.servlet.http.*;
import lombok.*;

@AllArgsConstructor
public class LoginServlet extends HttpServlet {

    private final UserService userService;
    private final SecurityService securityService;

    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("loginPage.html");
        response.getWriter().write(page);
    }

    @Override
    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User user = userService.get(login);
        if (user != null && securityService.verifyPassword(password, user.getSalt(), user.getPassword())) {
            securityService.createSession(response);
        }
        response.sendRedirect("/products");
    }
}