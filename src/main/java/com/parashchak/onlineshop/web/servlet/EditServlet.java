package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.service.SecurityService;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import com.parashchak.onlineshop.service.ProductService;
import jakarta.servlet.http.*;
import lombok.*;

import java.io.IOException;
import java.util.*;

import static com.parashchak.onlineshop.web.mapper.ProductRequestMapper.toProduct;

@AllArgsConstructor
public class EditServlet extends HttpServlet {

    private final PageGenerator pageGenerator = PageGenerator.instance();
    private final ProductService productService;
    private final SecurityService securityService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!securityService.validateSession(request)) {
            response.sendRedirect("/login");
        } else {
            Map<String, Object> pageData = new HashMap<>();
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = productService.getById(id);
            pageData.put("product", product);
            String page = pageGenerator.getPage("editPage.html", pageData);
            response.getWriter().write(page);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = toProduct(request);
        productService.update(product);
        String page = pageGenerator.getPage("editPageSuccessful.html");
        response.getWriter().write(page);
    }
}