package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.service.*;
import com.parashchak.templater.Templater;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

import static com.parashchak.onlineshop.web.mapper.ProductRequestMapper.toProduct;

public class EditServlet extends HttpServlet {

    private final Templater templater = new Templater("templates");
    private final ProductService productService = ServiceLocator.getService(ProductService.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageData = new HashMap<>();
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getById(id).orElseThrow();
        pageData.put("product", product);
        String page = templater.getPage("editPage.html", pageData);
        response.getWriter().write(page);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = toProduct(request);
        productService.update(product);
        String page = templater.getPage("editPageSuccessful.html");
        response.getWriter().write(page);
    }
}