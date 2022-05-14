package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.service.ProductService;
import com.parashchak.templater.Templater;
import jakarta.servlet.http.*;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.*;

@AllArgsConstructor
public class ViewAllServlet extends HttpServlet {

    private final Templater templater = new Templater("templates");
    private final ProductService productService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        showAllProducts(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.delete(id);
        showAllProducts(response);
    }

    private void showAllProducts(HttpServletResponse response) throws IOException {
        Map<String, Object> pageData = new HashMap<>();
        List<Product> allProducts = productService.getAll();
        pageData.put("products", allProducts);
        String page = templater.getPage("viewPage.html", pageData);
        response.getWriter().write(page);
    }
}