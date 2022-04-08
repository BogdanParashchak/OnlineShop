package com.parashchak.onlineshop.dao;

import com.parashchak.onlineshop.entity.Product;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductDaoITest {

    @Test
    void getAll() {
        ProductDao productDao = new ProductDao();
        List<Product> actualProducts = productDao.getAll();

        assertNotNull(actualProducts);
        assertFalse(actualProducts.isEmpty());
    }
}