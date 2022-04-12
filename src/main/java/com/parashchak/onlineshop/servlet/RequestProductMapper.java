package com.parashchak.onlineshop.servlet;

import com.parashchak.onlineshop.entity.Product;
import jakarta.servlet.http.HttpServletRequest;

public class RequestProductMapper {
    public static Product toProduct(HttpServletRequest request) {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            product.setId(id);
        }
        return product;
    }
}