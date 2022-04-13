package com.parashchak.onlineshop.servlet.mapper;

import com.parashchak.onlineshop.entity.Product;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

public class ProductRequestMapper {
    public static Product toProduct(HttpServletRequest request) {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCreationDate(LocalDateTime.now());
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            product.setId(id);
        }
        return product;
    }
}