package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.service.ProductService;
import com.parashchak.templater.Templater;
import jakarta.servlet.http.*;
import lombok.AllArgsConstructor;

import java.io.IOException;

import static com.parashchak.onlineshop.web.mapper.ProductRequestMapper.toProduct;

@AllArgsConstructor
public class AddServlet extends HttpServlet {

    private final Templater templater = new Templater("templates");
    private final ProductService productService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String page = templater.getPage("addPage.html");
        response.getWriter().write(page);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = toProduct(request);
        productService.add(product);
        String page = templater.getPage("addPageSuccessful.html");
        response.getWriter().write(page);
    }
}