package com.parashchak.onlineshop.dao;

import com.parashchak.onlineshop.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoITest {

    @Test
    @DisplayName("getAll returns not Null object on correctly pre-configured DB table")
    void givenPreparedPersonTable_WhenGetAll_ThenNotNullObjectReturned() {
        //prepare
        ProductDao productDao = new ProductDao();
        //when
        List<Product> actualProducts = productDao.getAll();
        //then
        assertNotNull(actualProducts);
    }

    @Test
    @DisplayName("getAll returns not empty product list on correctly pre-configured DB table")
    void givenPreparedPersonTable_WhenGetAll_ThenNotEmptyProductListReturned() {
        //prepare
        ProductDao productDao = new ProductDao();
        //when
        List<Product> actualProducts = productDao.getAll();
        //then
        assertFalse(actualProducts.isEmpty());
    }

    @Test
    @DisplayName("when add product to DB then returns inserted rows count")
    void givenPreparedPersonTable_WhenAddProduct_ThenInsertedRowsCountReturned() {
        //prepare
        Product product = new Product();
        product.setName("product");
        product.setPrice(50.00);
        ProductDao productDao = new ProductDao();
        //when
        int actualInsertedRowsCount = productDao.addProduct(product);
        //then
        assertEquals(1, actualInsertedRowsCount);
    }
}