package com.parashchak.onlineshop.dao.jdbc;

import com.parashchak.onlineshop.dao.ProductDao;
import com.parashchak.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import com.parashchak.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class JdbcProductDao implements ProductDao {

    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private static final String GET_ALL_PRODUCTS_QUERY = "SELECT id,name, price, creation_date, description FROM products ORDER BY id";
    private static final String ADD_PRODUCT_QUERY = "INSERT INTO products (name, price, creation_date, description) VALUES (?, ?, ?, ?)";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id=?";
    private static final String GET_PRODUCT_BY_ID_QUERY = "SELECT id, name, price, creation_date, description FROM products WHERE id=?";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET name=?,price=?, description=? WHERE id=?";
    private static final String SEARCH_PRODUCTS_QUERY = "SELECT id,name, price, creation_date, description FROM products WHERE name LIKE CONCAT( '%',?,'%') OR description LIKE CONCAT( '%',?,'%')";

    private final DataSource dataSource;

    public List<Product> getAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_PRODUCTS_QUERY)) {
            List<Product> allProductsList = new ArrayList<>();
            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                allProductsList.add(product);
            }
            log.info("Executed: {}", GET_ALL_PRODUCTS_QUERY);
            return allProductsList;
        } catch (Exception e) {
            throw new RuntimeException("Unable to get all products list from DB", e);
        }
    }

    public void add(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_QUERY)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(product.getCreationDate()));
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.executeUpdate();
            log.info("Executed: {}", preparedStatement);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add product to DB", e);
        }
    }

    public void delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_QUERY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            log.info("Executed: {}", preparedStatement);
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete product from DB", e);
        }
    }

    public Optional<Product> getById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                log.info("Executed: {}", preparedStatement);
                if (resultSet.next()) {
                    return Optional.of(PRODUCT_ROW_MAPPER.mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to get product from DB", e);
        }
    }

    public void update(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_QUERY)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getId());
            preparedStatement.executeUpdate();
            log.info("Executed: {}", preparedStatement);
        } catch (Exception e) {
            throw new RuntimeException("Unable to update product in DB", e);
        }
    }

    public List<Product> search(String searchText) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_PRODUCTS_QUERY)) {
            preparedStatement.setString(1, searchText);
            preparedStatement.setString(2, searchText);
            List<Product> productsSearchList = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                    productsSearchList.add(product);
                }
            }
            log.info("Executed: {}", preparedStatement);
            return productsSearchList;
        } catch (Exception e) {
            throw new RuntimeException("Unable to search products in DB", e);
        }
    }
}