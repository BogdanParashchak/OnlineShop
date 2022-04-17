package com.parashchak.onlineshop.web;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.presentation.PageGenerator;
import com.parashchak.onlineshop.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import static com.parashchak.onlineshop.web.mapper.ProductRequestMapper.toProduct;

@RequiredArgsConstructor
public class AddServlet extends HttpServlet {

    private final ProductService productService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addPage.html");
        response.getWriter().write(page);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = toProduct(request);
        productService.add(product);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addPageSuccessful.html");
        response.getWriter().write(page);
    }
}