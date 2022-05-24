package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.service.ProductService;
import com.parashchak.onlineshop.service.ServiceLocator;
import jakarta.servlet.http.*;

import java.io.IOException;

public class DeleteServlet extends HttpServlet {

    private final ProductService productService = ServiceLocator.getService(ProductService.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.delete(id);
        response.sendRedirect("/products");
    }
}