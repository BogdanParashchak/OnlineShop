package com.parashchak.onlineshop.dao;

import com.parashchak.onlineshop.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.parashchak.onlineshop.dao.ResultSetProductMapper.mapProduct;

public class ProductDao {

    private static final String GET_ALL_QUERY = "SELECT * FROM products";
    private static final String ADD_PRODUCT_QUERY = "INSERT INTO products (name, price) VALUES (?, ?)";

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
            throw new RuntimeException("Unable to get DB data", e);
        }
        return allProductsList;
    }

    public int addProduct(Product product){
        int insertedRows;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_QUERY);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            insertedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to add product to DB", e);
        }
        return insertedRows;
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql:online_shop";
        String username = "app";
        String password = "app";
        return DriverManager.getConnection(url, username, password);
    }
}