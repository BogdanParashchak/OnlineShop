package com.parashchak.onlineshop.dao.jdbc;

import com.parashchak.onlineshop.entity.Product;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDaoITest {

    private ProductDao productDao;

    @BeforeEach
    void SetUp() {
        Properties appProperties = new Properties();
        appProperties.setProperty("db.url", "jdbc:postgresql://localhost:5433/online_shop_test");
        appProperties.setProperty("db.user", "test");
        appProperties.setProperty("db.password", "test");

        productDao = new ProductDao(appProperties);

    }

    @Test
    @Order(1)
    @DisplayName("getAll gets not Null object from DB table")
    void givenPreparedPersonTable_WhenGetAll_ThenNotNullObjectReturned() {
        //when
        List<Product> actualProducts = productDao.getAll();
        //then
        assertNotNull(actualProducts);
    }

    @Test
    @Order(2)
    @DisplayName("getAll gets not empty product list from DB table")
    void givenPreparedPersonTable_WhenGetAll_ThenNotEmptyProductListReturned() {
        //when
        List<Product> actualProducts = productDao.getAll();
        //then
        assertFalse(actualProducts.isEmpty());
    }

    @Test
    @Order(3)
    @DisplayName("when add product to DB table then all product list size increases by 1")
    void givenPreparedPersonTable_WhenAddProduct_ThenAllProductListSizeIncreasesByOne() {
        //prepare
        Product product = new Product();
        product.setName("product");
        product.setPrice(50.00);
        product.setCreationDate(LocalDateTime.now());
        List<Product> productList = productDao.getAll();
        //when
        productDao.add(product);
        List<Product> productListAfterProductAdded = productDao.getAll();
        //then
        assertEquals(productList.size() + 1, productListAfterProductAdded.size());
    }

    @Test
    @Order(4)
    @DisplayName("when delete product from DB table then all product list size decreases by 1")
    void givenPreparedPersonTable_WhenDeleteProduct_ThenAllProductListSizeDecreasesByOne() {
        //prepare
        List<Product> productList = productDao.getAll();
        //when
        productDao.delete(1);
        List<Product> productListAfterProductDeleted = productDao.getAll();
        //then
        assertEquals(productList.size() - 1, productListAfterProductDeleted.size());
    }

    @Test
    @Order(5)
    @DisplayName("getById gets Product instance from DB table")
    void givenPreparedPersonTable_WhenGetProductById_ThenProductInstanceReturned() {
        //when
        Product actualProduct = productDao.getById(2);
        //then
        assertNotNull(actualProduct);
    }

    @Test
    @Order(6)
    @DisplayName("when update product in DB table all product list size does not change")
    void givenPreparedPersonTable_WhenUpdateProduct_ThenAllProductListSizeDoesNotChange() {
        //prepare
        Product product = new Product();
        product.setId(21);
        product.setName("product");
        product.setPrice(100.00);
        List<Product> productList = productDao.getAll();
        //when
        productDao.update(product);
        List<Product> productListAfterUpdated = productDao.getAll();
        //then
        assertEquals(productList.size(), productListAfterUpdated.size());
    }
}