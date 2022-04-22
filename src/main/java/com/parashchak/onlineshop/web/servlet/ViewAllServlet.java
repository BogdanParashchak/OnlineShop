package com.parashchak.onlineshop.web.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import com.parashchak.onlineshop.service.ProductService;
import jakarta.servlet.http.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.*;

@AllArgsConstructor
public class ViewAllServlet extends HttpServlet {

    private final ProductService productService;
    private List<String> sessionList;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean isValid = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName())) {
                    if (sessionList.contains(cookie.getValue())) {
                        isValid = true;
                    }
                    break;
                }
            }
        }

        if (!isValid) {
            response.sendRedirect("/login");
        }

        showAllProducts(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.delete(id);
        showAllProducts(response);
    }

    private void showAllProducts(HttpServletResponse response) throws IOException {
        Map<String, Object> pageData = new HashMap<>();
        List<Product> allProducts = productService.getAll();
        pageData.put("products", allProducts);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("viewAllPage.html", pageData);
        response.getWriter().write(page);
    }
}