package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.service.ProductService;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import jakarta.servlet.http.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.*;

@AllArgsConstructor
public class SearchServlet extends HttpServlet {

    private final PageGenerator pageGenerator = PageGenerator.instance();
    private final ProductService productService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageData = new HashMap<>();
        String searchText = request.getParameter("search-text");
        List<Product> productsSearchList = productService.search(searchText);
        pageData.put("products", productsSearchList);
        String page = pageGenerator.getPage("viewPage.html", pageData);
        response.getWriter().write(page);
    }
}