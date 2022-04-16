package com.parashchak.onlineshop.dao.jdbc;

import com.parashchak.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import com.parashchak.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@RequiredArgsConstructor
public class ProductDao {

    private final Properties configProperties;

    private static final String GET_ALL_PRODUCTS_QUERY = "SELECT id,name, price, creation_date FROM products ORDER BY id";
    private static final String ADD_PRODUCT_QUERY = "INSERT INTO products (name, price, creation_date) VALUES (?, ?, ?)";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id=?";
    private static final String GET_PRODUCT_BY_ID_QUERY = "SELECT id, name, price, creation_date FROM products WHERE id=?";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET name=?,price=? WHERE id=?";

    public List<Product> getAll() {
        List<Product> allProductsList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_PRODUCTS_QUERY);
            ProductRowMapper productRowMapper = new ProductRowMapper();
            while (resultSet.next()) {
                Product product = productRowMapper.mapRow(resultSet);
                allProductsList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get all products list from DB", e);
        }
        return allProductsList;
    }

    public void add(Product product) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_QUERY);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(product.getCreationDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to add product to DB", e);
        }
    }

    public void delete(int id) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete product from DB", e);
        }
    }

    public Product getById(int id) {
        Product product = null;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.getResultSet();
            ProductRowMapper productRowMapper = new ProductRowMapper();
            if (resultSet.next()) {
                product = productRowMapper.mapRow(resultSet);
            }

        } catch (Exception e) {
            throw new RuntimeException("Unable to get product from DB", e);
        }
        return product;
    }

    public void update(Product product) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_QUERY);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Unable to update product in DB", e);
        }
    }

    private Connection getConnection() throws SQLException {
        String url = configProperties.getProperty("db.url");
        String username = configProperties.getProperty("db.user");
        String password = configProperties.getProperty("db.password");
        return DriverManager.getConnection(url, username, password);
    }
}