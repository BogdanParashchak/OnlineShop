package com.parashchak.onlineshop.dao;

import lombok.*;

@AllArgsConstructor
public enum Query {
    GET_ALL_PRODUCTS_QUERY("SELECT * FROM products ORDER BY id"),
    ADD_PRODUCT_QUERY("INSERT INTO products (name, price) VALUES (?, ?)"),
    DELETE_PRODUCT_QUERY("DELETE FROM products WHERE id=?"),
    GET_PRODUCT_BY_ID_QUERY("SELECT * FROM products WHERE id=?"),
    UPDATE_PRODUCT_QUERY("UPDATE products SET name=?,price=? WHERE id=?");

    @Getter private final String description;
}