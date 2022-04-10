package com.parashchak.onlineshop.dao;

import com.parashchak.onlineshop.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.parashchak.onlineshop.dao.ResultSetProductMapper.mapProduct;

public class ProductDao {

    public List<Product> getAllProducts() {
        List<Product> allProductsList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Query.GET_ALL_PRODUCTS_QUERY.getDescription());
           while (resultSet.next()) {
                Product product = mapProduct(resultSet);
                allProductsList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get all products list from DB", e);
        }
        return allProductsList;
    }

    public int addProduct(Product product){
        int addedRows;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.ADD_PRODUCT_QUERY.getDescription());
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            addedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to add product to DB", e);
        }
        return addedRows;
    }

    public int deleteProduct(int id) {
        int deletedRows;
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(Query.DELETE_PRODUCT_QUERY.getDescription());
            preparedStatement.setInt(1, id);
            deletedRows = preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete product from DB", e);
        }
        return deletedRows;
    }

    public Product getProductById(int id) {
        Product product=null;
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(Query.GET_PRODUCT_BY_ID_QUERY.getDescription());
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                product = mapProduct(resultSet);
            }

        } catch (Exception e) {
            throw new RuntimeException("Unable to get product from DB", e);
        }
        return product;
    }

    public int updateProduct(Product product) {
        int updatedRows;
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(Query.UPDATE_PRODUCT_QUERY.getDescription());
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getId());
            updatedRows = preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Unable to update product in DB", e);
        }
        return updatedRows;
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql:online_shop";
        String username = "app";
        String password = "app";
        return DriverManager.getConnection(url, username, password);
    }
}