package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.service.ProductService;
import jakarta.servlet.http.*;
import lombok.AllArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
public class DeleteServlet extends HttpServlet {

    private final ProductService productService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            int id = Integer.parseInt(request.getParameter("id"));
            productService.delete(id);
            response.sendRedirect("/products");
    }
}