package com.parashchak.onlineshop.dao;

import com.parashchak.onlineshop.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.parashchak.onlineshop.dao.ProductMapper.mapProduct;

public class ProductDao {

    private static final String GET_ALL_QUERY = "SELECT * FROM products";

    public List<Product> getAll() {
        List<Product> allProductsList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_QUERY);
           while (resultSet.next()) {
                Product product = mapProduct(resultSet);
                allProductsList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL data unavailable", e);
        }
        return allProductsList;
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql:online_shop";
        String username = "app";
        String password = "app";
        return DriverManager.getConnection(url, username, password);
    }
}