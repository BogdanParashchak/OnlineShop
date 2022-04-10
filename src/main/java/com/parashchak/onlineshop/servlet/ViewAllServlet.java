package com.parashchak.onlineshop.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.presentation.PageGenerator;
import com.parashchak.onlineshop.service.ProductService;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

public class ViewAllServlet extends HttpServlet {

    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        showAllProducts(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.deleteProduct(id);
        showAllProducts(response);
    }

    private void showAllProducts(HttpServletResponse response) throws IOException {
        Map<String, Object> pageData = new HashMap<>();
        List<Product> allProducts = productService.getAll();
        pageData.put("products", allProducts);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("viewAllPage.html", pageData);
        response.getWriter().write(page);
    }
}