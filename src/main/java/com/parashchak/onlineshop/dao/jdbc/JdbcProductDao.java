package com.parashchak.onlineshop.dao.jdbc;

import com.parashchak.onlineshop.dao.ProductDao;
import com.parashchak.onlineshop.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class JdbcProductDao implements ProductDao {

    private static final String GET_ALL_PRODUCTS_QUERY = "SELECT id,name, price, creation_date, description FROM products ORDER BY id";
    private static final String ADD_PRODUCT_QUERY = "INSERT INTO products (name, price, creation_date, description) VALUES (?, ?, ?, ?)";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id=?";
    private static final String GET_PRODUCT_BY_ID_QUERY = "SELECT id, name, price, creation_date, description FROM products WHERE id=?";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET name=?,price=?, description=? WHERE id=?";
    private static final String SEARCH_PRODUCTS_QUERY = "SELECT * FROM products WHERE name LIKE CONCAT( '%',?,'%') OR description LIKE CONCAT( '%',?,'%')";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> getAll() {
        return jdbcTemplate.query(GET_ALL_PRODUCTS_QUERY, new BeanPropertyRowMapper<>(Product.class), null);
    }

    public void add(Product product) {
        jdbcTemplate.update(ADD_PRODUCT_QUERY, product.getName(), product.getPrice(),
                product.getCreationDate(), product.getDescription());
    }

    public void delete(int id) {
        jdbcTemplate.update(DELETE_PRODUCT_QUERY, id);
    }

    public Optional<Product> findById(int id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(GET_PRODUCT_BY_ID_QUERY,
                    new BeanPropertyRowMapper<>(Product.class), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void update(Product product) {
        jdbcTemplate.update(UPDATE_PRODUCT_QUERY, product.getName(), product.getPrice(),
                product.getDescription(), product.getId());
    }

    public List<Product> search(String searchText) {
        return jdbcTemplate.query(SEARCH_PRODUCTS_QUERY, new BeanPropertyRowMapper<>(Product.class), searchText, searchText);
    }
}