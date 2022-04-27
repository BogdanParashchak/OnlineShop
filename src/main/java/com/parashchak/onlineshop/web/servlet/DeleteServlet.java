package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.service.ProductService;
import com.parashchak.onlineshop.service.SecurityService;
import jakarta.servlet.http.*;
import lombok.*;

import java.io.IOException;

@AllArgsConstructor
public class DeleteServlet extends HttpServlet {

    private final ProductService productService;
    private final SecurityService securityService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!securityService.validateSession(request)) {
            response.sendRedirect("/login");
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            productService.delete(id);
            response.sendRedirect("/products");
        }
    }
}