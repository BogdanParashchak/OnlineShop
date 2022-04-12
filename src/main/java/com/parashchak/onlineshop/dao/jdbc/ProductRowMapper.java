package com.parashchak.onlineshop.dao.jdbc;

import com.parashchak.onlineshop.entity.Product;

import java.sql.*;

public class ProductRowMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        return product;
    }
}