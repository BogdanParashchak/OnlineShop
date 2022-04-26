package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.service.SecurityService;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import com.parashchak.onlineshop.service.ProductService;
import jakarta.servlet.http.*;
import lombok.*;

import java.util.*;

import static com.parashchak.onlineshop.web.mapper.ProductRequestMapper.toProduct;

@RequiredArgsConstructor
@AllArgsConstructor
public class EditServlet extends HttpServlet {

    private final ProductService productService;
    private SecurityService securityService;

    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (!securityService.validateSession(request)) {
            response.sendRedirect("/login");
        } else {
            Map<String, Object> pageData = new HashMap<>();
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = productService.getById(id);
            pageData.put("product", product);
            PageGenerator pageGenerator = PageGenerator.instance();
            String page = pageGenerator.getPage("editPage.html", pageData);
            response.getWriter().write(page);
        }
    }

    @Override
    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Product product = toProduct(request);
        productService.update(product);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("editPageSuccessful.html");
        response.getWriter().write(page);
    }
}