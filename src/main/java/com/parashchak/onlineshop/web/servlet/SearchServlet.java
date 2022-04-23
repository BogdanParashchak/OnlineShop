package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.service.ProductService;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

public class SearchServlet extends HttpServlet {
    private final ProductService productService;

    public SearchServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageData = new HashMap<>();
        String searchText = request.getParameter("search-text");
        List<Product> productsSearchList = productService.search(searchText);
        pageData.put("products", productsSearchList);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("viewPage.html", pageData);
        response.getWriter().write(page);
    }
}