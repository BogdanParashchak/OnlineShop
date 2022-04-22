package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import com.parashchak.onlineshop.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.*;

import static com.parashchak.onlineshop.web.mapper.ProductRequestMapper.toProduct;

@RequiredArgsConstructor
public class EditServlet extends HttpServlet {

    private final ProductService productService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageData = new HashMap<>();
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getById(id);
        pageData.put("product", product);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("editPage.html", pageData);
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = toProduct(request);
        productService.update(product);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("editPageSuccessful.html");
        response.getWriter().write(page);
    }
}