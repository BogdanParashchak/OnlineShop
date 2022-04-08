package com.parashchak.onlineshop.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.presentation.PageGenerator;
import com.parashchak.onlineshop.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAllServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageData = new HashMap<>();
        List<Product> allProducts = productService.getAll();
        pageData.put("products", allProducts);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("page.html", pageData);
        response.getWriter().write(page);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}