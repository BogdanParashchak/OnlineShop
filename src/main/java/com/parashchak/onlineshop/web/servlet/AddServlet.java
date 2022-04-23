package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import com.parashchak.onlineshop.service.ProductService;

import java.util.List;

import jakarta.servlet.http.*;
import lombok.*;

import static com.parashchak.onlineshop.web.mapper.ProductRequestMapper.toProduct;
import static com.parashchak.onlineshop.web.validator.CookieValidator.validateCookie;

@RequiredArgsConstructor
@AllArgsConstructor
public class AddServlet extends HttpServlet {

    private final ProductService productService;
    private List<String> sessionList;

    @Override
    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        validateCookie(request, response, sessionList);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addPage.html");
        response.getWriter().write(page);
    }

    @Override
    @SneakyThrows
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Product product = toProduct(request);
        productService.add(product);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addPageSuccessful.html");
        response.getWriter().write(page);
    }
}