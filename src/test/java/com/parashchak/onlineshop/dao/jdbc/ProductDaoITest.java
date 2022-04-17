package com.parashchak.onlineshop.dao.jdbc;

import com.parashchak.onlineshop.entity.Product;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDaoITest {

    private JdbcProductDao jdbcProductDao;
    JdbcConnectionFactory jdbcConnectionFactory;

    @BeforeEach
    void SetUp() {
        Properties appProperties = new Properties();
        appProperties.setProperty("db.url", "jdbc:postgresql://localhost:5433/online_shop_test");
        appProperties.setProperty("db.user", "test");
        appProperties.setProperty("db.password", "test");

        jdbcConnectionFactory = new JdbcConnectionFactory(appProperties);
        jdbcProductDao = new JdbcProductDao(jdbcConnectionFactory);
    }

    @Test
    @Order(1)
    @DisplayName("getAll gets not Null object from DB table")
    void givenPreparedPersonTable_WhenGetAll_ThenNotNullObjectReturned() {
        //when
        List<Product> actualProducts = jdbcProductDao.getAll();
        //then
        assertNotNull(actualProducts);
    }

    @Test
    @Order(2)
    @DisplayName("getAll gets not empty product list from DB table")
    void givenPreparedPersonTable_WhenGetAll_ThenNotEmptyProductListReturned() {
        //when
        List<Product> actualProducts = jdbcProductDao.getAll();
        //then
        assertFalse(actualProducts.isEmpty());
    }

    @Test
    @Order(3)
    @DisplayName("when add product to DB table then all product list size increases by 1")
    void givenPreparedPersonTable_WhenAddProduct_ThenAllProductListSizeIncreasesByOne() {
        //prepare
        Product product = Product.builder()
                .name("product")
                .price(50)
                .creationDate(LocalDateTime.now())
                .build();

        List<Product> productList = jdbcProductDao.getAll();
        //when
        jdbcProductDao.add(product);
        List<Product> productListAfterProductAdded = jdbcProductDao.getAll();
        //then
        assertEquals(productList.size() + 1, productListAfterProductAdded.size());
    }

    @Test
    @Order(4)
    @DisplayName("when delete product from DB table then all product list size decreases by 1")
    void givenPreparedPersonTable_WhenDeleteProduct_ThenAllProductListSizeDecreasesByOne() {
        //prepare
        List<Product> productList = jdbcProductDao.getAll();
        //when
        jdbcProductDao.delete(1);
        List<Product> productListAfterProductDeleted = jdbcProductDao.getAll();
        //then
        assertEquals(productList.size() - 1, productListAfterProductDeleted.size());
    }

    @Test
    @Order(5)
    @DisplayName("getById gets Product instance from DB table")
    void givenPreparedPersonTable_WhenGetProductById_ThenProductInstanceReturned() {
        //when
        Product actualProduct = jdbcProductDao.getById(2);
        //then
        assertNotNull(actualProduct);
    }

    @Test
    @Order(6)
    @DisplayName("when update product in DB table then all product list size does not change")
    void givenPreparedPersonTable_WhenUpdateProduct_ThenAllProductListSizeDoesNotChange() {
        //prepare
        Product product = Product.builder()
                .id(21)
                .name("product")
                .price(100)
                .build();

        List<Product> productList = jdbcProductDao.getAll();
        //when
        jdbcProductDao.update(product);
        List<Product> productListAfterUpdated = jdbcProductDao.getAll();
        //then
        assertEquals(productList.size(), productListAfterUpdated.size());
    }
}