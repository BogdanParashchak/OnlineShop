package com.parashchak.onlineshop.dao;

import com.parashchak.onlineshop.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetProductMapper {
    public static Product mapProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        return product;
    }
}