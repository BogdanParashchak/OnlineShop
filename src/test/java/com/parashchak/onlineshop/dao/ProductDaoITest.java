package com.parashchak.onlineshop.dao;

import com.parashchak.onlineshop.entity.Product;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDaoITest {

    ProductDao productDao = new ProductDao();

    @Test
    @Order(1)
    @DisplayName("getAllProducts gets not Null object from DB table")
    void givenPreparedPersonTable_WhenGetAll_ThenNotNullObjectReturned() {
        //when
        List<Product> actualProducts = productDao.getAllProducts();
        //then
        assertNotNull(actualProducts);
    }

    @Test
    @Order(2)
    @DisplayName("getAllProducts gets not empty product list from DB table")
    void givenPreparedPersonTable_WhenGetAll_ThenNotEmptyProductListReturned() {
        //when
        List<Product> actualProducts = productDao.getAllProducts();
        //then
        assertFalse(actualProducts.isEmpty());
    }

    @Test
    @Order(3)
    @DisplayName("when add product to DB table then returns 1")
    void givenPreparedPersonTable_WhenAddProduct_ThenReturnsOne() {
        //prepare
        Product product = new Product();
        product.setName("product");
        product.setPrice(50.00);
        //when
        int actualInsertedRowsCount = productDao.addProduct(product);
        //then
        assertEquals(1, actualInsertedRowsCount);
    }

    @Test
    @Order(4)
    @DisplayName("when delete product from DB table then returns 1")
    void givenPreparedPersonTable_WhenDeleteProduct_ThenReturnsOne() {
        //prepare
        List<Product> products = productDao.getAllProducts();
        assertFalse(products.isEmpty());
        //when
        int actualDeletedRowsCount = productDao.deleteProduct(4);
        //then
        assertEquals(1, actualDeletedRowsCount);
    }

    @Test
    @Order(5)
    @DisplayName("getProductById gets Product instance from DB table")
    void givenPreparedPersonTable_WhenGetProductById_ThenProductInstanceReturned() {
        //when
        Product actualProduct = productDao.getProductById(1);
        //then
        assertNotNull(actualProduct);
    }

    @Test
    @Order(6)
    @DisplayName("when update product int DB table then returns 1")
    void givenPreparedPersonTable_WhenUpdateProduct_ThenReturnsOne() {
        //prepare
        Product product = new Product();
        product.setId(1);
        product.setName("MILK");
        product.setPrice(100.00);
        //when
        int actualUpdatedRowsCount = productDao.updateProduct(product);
        //then
        assertEquals(1, actualUpdatedRowsCount);
    }
}