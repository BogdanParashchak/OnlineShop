package com.parashchak.onlineshop.web.mapper;

import com.parashchak.onlineshop.entity.Product;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class ProductRequestMapper {
    public static Product toProduct(HttpServletRequest request) {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");

        Product product = Product.builder()
                .name(name)
                .price(price)
                .creationDate(LocalDateTime.now())
                .description(description)
                .build();

        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            product.setId(id);
        }
        return product;
    }
}