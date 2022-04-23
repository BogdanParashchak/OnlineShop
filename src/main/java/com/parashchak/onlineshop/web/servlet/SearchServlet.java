package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.service.ProductService;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
public class SearchServlet extends HttpServlet {

    private final ProductService productService;

    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> pageData = new HashMap<>();
        String searchText = request.getParameter("search-text");
        List<Product> productsSearchList = productService.search(searchText);
        pageData.put("products", productsSearchList);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("viewPage.html", pageData);
        response.getWriter().write(page);
    }
}