package com.parashchak.onlineshop.dao.jdbc;

import com.parashchak.onlineshop.entity.Product;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JdbcProductDaoITest {

    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "app";
    private static final String PASSWORD = "app";

    private final Properties properties = new Properties();
    JdbcConnectionFactory jdbcConnectionFactory;
    JdbcProductDao jdbcProductDao;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        properties.setProperty("db.url", URL);
        properties.setProperty("db.user", USER);
        properties.setProperty("db.password", PASSWORD);

        jdbcConnectionFactory = new JdbcConnectionFactory(properties);
        jdbcProductDao = new JdbcProductDao(jdbcConnectionFactory);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS products;");
            statement.execute("CREATE TABLE products (id SERIAL PRIMARY KEY, name VARCHAR(50) NOT NULL, price NUMERIC(8,2) NOT NULL, creation_date TIMESTAMP);");
            statement.execute("INSERT INTO products (name, price, creation_date) VALUES ('Mazda', 43032.25, now());");
            statement.execute("INSERT INTO products (name, price, creation_date) VALUES ('Ford', 74582.01, now());");
            statement.execute("INSERT INTO products (name, price, creation_date) VALUES ('BMW', 52782.51, now());");
        }
    }

    @Test
    @DisplayName("getAll returns not Null object")
    void givenTable_whenGetAll_thenNotNullObjectReturned() {
        List<Product> products = jdbcProductDao.getAll();
        assertNotNull(products);
    }

    @Test
    @DisplayName("getAll returns list known size")
    void givenTable_whenGetAll_thenListOfKnownSizeReturned() {
        List<Product> products = jdbcProductDao.getAll();
        assertEquals(3, products.size());
    }


    @Test
    @DisplayName("getAll returns list of actual products")
    void givenTable_whenGetAll_thenListOfActualProductsReturned() {
        List<Product> products = jdbcProductDao.getAll();

        assertEquals(1, products.get(0).getId());
        assertEquals(2, products.get(1).getId());
        assertEquals(3, products.get(2).getId());

        assertEquals("Mazda", products.get(0).getName());
        assertEquals("Ford", products.get(1).getName());
        assertEquals("BMW", products.get(2).getName());

        assertEquals(43032.25, products.get(0).getPrice());
        assertEquals(74582.01, products.get(1).getPrice());
        assertEquals(52782.51, products.get(2).getPrice());
    }

    @Test
    @DisplayName("getAll does not change products' list size")
    void givenTable_whenGetAll_thenSizeOfProductsListDoesNotChange() {
        List<Product> productsListAfterFirstCall = jdbcProductDao.getAll();
        List<Product> productsListAfterSecondCall = jdbcProductDao.getAll();

        assertEquals(3, productsListAfterFirstCall.size());
        assertEquals(3, productsListAfterSecondCall.size());
    }

    @Test
    @DisplayName("add increases products' list size by one")
    void givenTable_whenAdd_thenSizeOfProductsListIncreasesByOne() {
        //prepare
        Product product = Product.builder().
                name("Lexus").
                price(96054.39).
                creationDate(LocalDateTime.now()).
                build();
        int initialListSize = jdbcProductDao.getAll().size();

        //when
        jdbcProductDao.add(product);

        //then
        int afterAddListSize = jdbcProductDao.getAll().size();
        assertEquals(4, afterAddListSize);
        assertEquals(initialListSize + 1, afterAddListSize);
    }

    @Test
    @DisplayName("add does not change previously existing products")
    void givenTable_whenAdd_thenPreviouslyExistingProductsDoesNotChange() {
        //prepare
        Product product = Product.builder().
                name("Lexus").
                price(96054.39).
                creationDate(LocalDateTime.now()).
                build();

        //when
        jdbcProductDao.add(product);

        //then
        List<Product> products = jdbcProductDao.getAll();

        assertEquals(1, products.get(0).getId());
        assertEquals(2, products.get(1).getId());
        assertEquals(3, products.get(2).getId());

        assertEquals("Mazda", products.get(0).getName());
        assertEquals("Ford", products.get(1).getName());
        assertEquals("BMW", products.get(2).getName());

        assertEquals(43032.25, products.get(0).getPrice());
        assertEquals(74582.01, products.get(1).getPrice());
        assertEquals(52782.51, products.get(2).getPrice());
    }

    @Test
    @DisplayName("add creates row in products' table ")
    void givenTable_whenAdd_thenProductAddedInTable() {
        //prepare
        Product product = Product.builder().
                name("Lexus").
                price(96054.39).
                creationDate(LocalDateTime.now()).
                build();

        //when
        jdbcProductDao.add(product);

        //then
        List<Product> products = jdbcProductDao.getAll();
        Product addedProduct = products.get(3);

        assertEquals(4, addedProduct.getId());
        assertEquals("Lexus", addedProduct.getName());
        assertEquals(96054.39, addedProduct.getPrice());
    }

    @Test
    @DisplayName("delete decreases products' list size by one")
    void givenTable_whenDelete_thenSizeOfProductsListDecreasesByOne() {
        //prepare
        int initialListSize = jdbcProductDao.getAll().size();

        //when
        jdbcProductDao.delete(1);

        //then
        int afterDeleteListSize = jdbcProductDao.getAll().size();
        assertEquals(2, afterDeleteListSize);
        assertEquals(initialListSize - 1, afterDeleteListSize);
    }

    @Test
    @DisplayName("delete does not change previously existing products")
    void givenTable_whenDelete_thenPreviouslyExistingProductsDoesNotChange() {
        jdbcProductDao.delete(2);

        List<Product> products = jdbcProductDao.getAll();

        assertEquals(1, products.get(0).getId());
        assertEquals(3, products.get(1).getId());

        assertEquals("Mazda", products.get(0).getName());
        assertEquals("BMW", products.get(1).getName());

        assertEquals(43032.25, products.get(0).getPrice());
        assertEquals(52782.51, products.get(1).getPrice());
    }

    @Test
    @DisplayName("getById returns not Null object")
    void givenTable_whenGetById_thenNotNullObjectReturned() {
        assertNotNull(jdbcProductDao.getById(1));
        assertNotNull(jdbcProductDao.getById(2));
        assertNotNull(jdbcProductDao.getById(3));
    }

    @Test
    @DisplayName("getById returns actual product")
    void givenTable_whenGetById_thenActualProductReturned() {
        Product actualProduct = jdbcProductDao.getById(2);

        assertEquals(2, actualProduct.getId());
        assertEquals("Ford", actualProduct.getName());
        assertEquals(74582.01, actualProduct.getPrice());
    }

    @Test
    @DisplayName("getById does not change previously existing products")
    void givenTable_whenGetById_thenPreviouslyExistingProductsDoesNotChange() {
        Product actualProduct = jdbcProductDao.getById(2);

        List<Product> products = jdbcProductDao.getAll();

        assertEquals(1, products.get(0).getId());
        assertEquals(3, products.get(2).getId());

        assertEquals("Mazda", products.get(0).getName());
        assertEquals("BMW", products.get(2).getName());

        assertEquals(43032.25, products.get(0).getPrice());
        assertEquals(52782.51, products.get(2).getPrice());
    }

    @Test
    @DisplayName("update does not change products' list size")
    void givenTable_whenUpdate_thenSizeOfProductsListDoesNotChange() {

        //prepare
        int initialListSize = jdbcProductDao.getAll().size();

        Product product = Product.builder().
                id(2).
                name("Lexus").
                price(96054.39).
                creationDate(LocalDateTime.now()).
                build();

        //when
        jdbcProductDao.update(product);

        //then
        int listSizeAfterUpdate = jdbcProductDao.getAll().size();
        assertEquals(initialListSize, listSizeAfterUpdate);
        assertEquals(3, listSizeAfterUpdate);
    }

    @Test
    @DisplayName("update does not change previously existing products")
    void givenTable_whenUpdate_thenPreviouslyExistingProductsDoesNotChange() {

        //prepare
        Product product = Product.builder().
                id(2).
                name("Lexus").
                price(96054.39).
                creationDate(LocalDateTime.now()).
                build();

        //when
        jdbcProductDao.update(product);

        //then
        List<Product> products = jdbcProductDao.getAll();

        assertEquals(1, products.get(0).getId());
        assertEquals(3, products.get(2).getId());

        assertEquals("Mazda", products.get(0).getName());
        assertEquals("BMW", products.get(2).getName());

        assertEquals(43032.25, products.get(0).getPrice());
        assertEquals(52782.51, products.get(2).getPrice());
    }

    @Test
    @DisplayName("update updates row in products' table")
    void givenTable_whenUpdate_thenProductUpdated() {

        //prepare
        Product product = Product.builder().
                id(2).
                name("Lexus").
                price(96054.39).
                creationDate(LocalDateTime.now()).
                build();

        //when
        jdbcProductDao.update(product);

        //then
        List<Product> products = jdbcProductDao.getAll();

        assertEquals(2, products.get(1).getId());
        assertEquals("Lexus", products.get(1).getName());
        assertEquals(96054.39, products.get(1).getPrice());
    }
}