package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.service.UserService;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import jakarta.servlet.http.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class LoginServlet extends HttpServlet {

    private final UserService userService;
    private final List<String> sessionList;

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
        if (userService.validate(login, password)) {
            String uuid = UUID.randomUUID().toString();
            sessionList.add(uuid);
            Cookie cookie = new Cookie("user-token", uuid);
            response.addCookie(cookie);
        }
        response.sendRedirect("/products");
    }
}