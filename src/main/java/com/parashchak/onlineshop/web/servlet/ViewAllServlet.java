package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import com.parashchak.onlineshop.service.ProductService;
import jakarta.servlet.http.*;
import lombok.*;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
public class ViewAllServlet extends HttpServlet {

    private final ProductService productService;

    @Override
    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        showAllProducts(response);
    }

    @Override
    @SneakyThrows
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.delete(id);
        showAllProducts(response);
    }

    private void showAllProducts(HttpServletResponse response) throws IOException {
        Map<String, Object> pageData = new HashMap<>();
        List<Product> allProducts = productService.getAll();
        pageData.put("products", allProducts);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("viewPage.html", pageData);
        response.getWriter().write(page);
    }
}