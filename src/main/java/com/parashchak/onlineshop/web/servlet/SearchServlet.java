package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.service.*;
import com.parashchak.templater.Templater;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

public class SearchServlet extends HttpServlet {

    private final Templater templater = new Templater("templates");
    private final ProductService productService = ServiceLocator.getService(ProductService.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageData = new HashMap<>();
        String searchText = request.getParameter("search-text");
        List<Product> productsSearchList = productService.search(searchText);
        pageData.put("products", productsSearchList);
        String page = templater.getPage("viewPage.html", pageData);
        response.getWriter().write(page);
    }
}