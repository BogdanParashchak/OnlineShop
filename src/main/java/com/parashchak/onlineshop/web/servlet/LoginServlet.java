package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.web.presentation.PageGenerator;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.*;

@RequiredArgsConstructor
public class LoginServlet extends HttpServlet {

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
        String uuid = UUID.randomUUID().toString();
        sessionList.add(uuid);
        Cookie cookie = new Cookie("user-token", uuid);
        response.addCookie(cookie);
        response.sendRedirect("/products");
    }
}