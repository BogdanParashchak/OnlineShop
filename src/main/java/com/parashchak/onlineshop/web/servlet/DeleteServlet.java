package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.service.ProductService;
import com.parashchak.onlineshop.service.SecurityService;
import jakarta.servlet.http.*;
import lombok.*;

@AllArgsConstructor
public class DeleteServlet extends HttpServlet {

    private final ProductService productService;
    private SecurityService securityService;

    @Override
    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (!securityService.validateSession(request)) {
            response.sendRedirect("/login");
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            productService.delete(id);
            response.sendRedirect("/products");
        }
    }
}