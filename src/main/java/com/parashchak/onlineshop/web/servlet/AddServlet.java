package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.security.SecurityService;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import com.parashchak.onlineshop.service.ProductService;

import com.parashchak.onlineshop.web.util.RequestUtil;
import jakarta.servlet.http.*;
import lombok.*;

import java.io.IOException;
import java.util.Optional;

import static com.parashchak.onlineshop.web.mapper.ProductRequestMapper.toProduct;

@AllArgsConstructor
public class AddServlet extends HttpServlet {

    private final PageGenerator pageGenerator = PageGenerator.instance();
    private final ProductService productService;
    private final SecurityService securityService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<String> userToken = RequestUtil.getUserToken(request);
        if (!securityService.validateUserToken(userToken)) {
            response.sendRedirect("/login");
        } else {
            String page = pageGenerator.getPage("addPage.html");
            response.getWriter().write(page);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = toProduct(request);
        productService.add(product);
        String page = pageGenerator.getPage("addPageSuccessful.html");
        response.getWriter().write(page);
    }
}