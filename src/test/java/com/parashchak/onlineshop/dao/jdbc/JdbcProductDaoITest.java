package com.parashchak.onlineshop.dao.jdbc;

import com.parashchak.onlineshop.dao.ProductDao;
import com.parashchak.onlineshop.entity.Product;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JdbcProductDaoITest {

    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "app";
    private static final String PASSWORD = "app";

    private ProductDao productDao;

    @BeforeEach
    @SneakyThrows
    void setUp() {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(URL);
        hikariConfig.setUsername(USER);
        hikariConfig.setPassword(PASSWORD);

        DataSource dataSource = new HikariDataSource(hikariConfig);
        productDao = new JdbcProductDao(dataSource);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS products;");
            statement.execute("CREATE TABLE products (id SERIAL PRIMARY KEY, name VARCHAR(50) NOT NULL, price NUMERIC(8,2) NOT NULL, creation_date TIMESTAMP, description VARCHAR(100));");
            statement.execute("INSERT INTO products (name, price, creation_date, description) VALUES ('Mazda', 43032.25, now(), 'first_product_description');");
            statement.execute("INSERT INTO products (name, price, creation_date, description) VALUES ('Ford', 74582.01, now(), 'second_product_description');");
            statement.execute("INSERT INTO products (name, price, creation_date, description) VALUES ('BMW', 52782.51, now(), 'third_product_description');");
        }
    }

    @Test
    @DisplayName("getAll returns not Null object")
    void givenTable_whenGetAll_thenNotNullObjectReturned() {
        List<Product> products = productDao.getAll();
        assertNotNull(products);
    }

    @Test
    @DisplayName("getAll returns list of known size")
    void givenTable_whenGetAll_thenListOfKnownSizeReturned() {
        List<Product> products = productDao.getAll();
        assertEquals(3, products.size());
    }

    @Test
    @DisplayName("getAll returns list of actual products")
    void givenTable_whenGetAll_thenListOfActualProductsReturned() {
        List<Product> products = productDao.getAll();

        assertEquals(1, products.get(0).getId());
        assertEquals(2, products.get(1).getId());
        assertEquals(3, products.get(2).getId());

        assertEquals("Mazda", products.get(0).getName());
        assertEquals("Ford", products.get(1).getName());
        assertEquals("BMW", products.get(2).getName());

        assertEquals(43032.25, products.get(0).getPrice());
        assertEquals(74582.01, products.get(1).getPrice());
        assertEquals(52782.51, products.get(2).getPrice());

        assertEquals("first_product_description", products.get(0).getDescription());
        assertEquals("second_product_description", products.get(1).getDescription());
        assertEquals("third_product_description", products.get(2).getDescription());
    }

    @Test
    @DisplayName("getAll does not change products' list size")
    void givenTable_whenGetAll_thenSizeOfProductsListDoesNotChange() {
        List<Product> productsListAfterFirstCall = productDao.getAll();
        List<Product> productsListAfterSecondCall = productDao.getAll();

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
                description("description").
                build();
        int initialListSize = productDao.getAll().size();

        //when
        productDao.add(product);

        //then
        int afterAddListSize = productDao.getAll().size();
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
                description("description").
                build();

        //when
        productDao.add(product);

        //then
        List<Product> products = productDao.getAll();

        assertEquals(1, products.get(0).getId());
        assertEquals(2, products.get(1).getId());
        assertEquals(3, products.get(2).getId());

        assertEquals("Mazda", products.get(0).getName());
        assertEquals("Ford", products.get(1).getName());
        assertEquals("BMW", products.get(2).getName());

        assertEquals(43032.25, products.get(0).getPrice());
        assertEquals(74582.01, products.get(1).getPrice());
        assertEquals(52782.51, products.get(2).getPrice());

        assertEquals("first_product_description", products.get(0).getDescription());
        assertEquals("second_product_description", products.get(1).getDescription());
        assertEquals("third_product_description", products.get(2).getDescription());
    }

    @Test
    @DisplayName("add creates row in products' table ")
    void givenTable_whenAdd_thenProductAddedInTable() {
        //prepare
        Product product = Product.builder().
                name("Lexus").
                price(96054.39).
                creationDate(LocalDateTime.now()).
                description("description").
                build();

        //when
        productDao.add(product);

        //then
        List<Product> products = productDao.getAll();
        Product addedProduct = products.get(3);

        assertEquals(4, addedProduct.getId());
        assertEquals("Lexus", addedProduct.getName());
        assertEquals(96054.39, addedProduct.getPrice());
        assertEquals("description", addedProduct.getDescription());
    }

    @Test
    @DisplayName("delete decreases products' list size by one")
    void givenTable_whenDelete_thenSizeOfProductsListDecreasesByOne() {
        //prepare
        int initialListSize = productDao.getAll().size();

        //when
        productDao.delete(1);

        //then
        int afterDeleteListSize = productDao.getAll().size();
        assertEquals(2, afterDeleteListSize);
        assertEquals(initialListSize - 1, afterDeleteListSize);
    }

    @Test
    @DisplayName("delete does not change previously existing products")
    void givenTable_whenDelete_thenPreviouslyExistingProductsDoesNotChange() {
        productDao.delete(2);

        List<Product> products = productDao.getAll();

        assertEquals(1, products.get(0).getId());
        assertEquals(3, products.get(1).getId());

        assertEquals("Mazda", products.get(0).getName());
        assertEquals("BMW", products.get(1).getName());

        assertEquals(43032.25, products.get(0).getPrice());
        assertEquals(52782.51, products.get(1).getPrice());

        assertEquals("first_product_description", products.get(0).getDescription());
        assertEquals("third_product_description", products.get(1).getDescription());
    }

    @Test
    @DisplayName("getById returns not Null optional product for existing product")
    void givenTableContainingProducts_whenGetExistingProductById_thenNotNullOptionalProductReturned() {
        Optional<Product> optionalProduct = productDao.getById(1);
        assertNotNull(optionalProduct);
    }

    @Test
    @DisplayName("getById returns not empty optional product for existing product")
    void givenTableContainingProducts_whenGetExistingProductById_thenNotEmptyOptionalProductReturned() {
        Optional<Product> optionalProduct = productDao.getById(1);
        assertFalse(optionalProduct.isEmpty());
    }

    @Test
    @DisplayName("getById returns not Null optional product for non-existing product")
    void givenTableContainingProducts_whenGetNonExistingProductById_thenNotNullOptionalObjectReturned() {
        assertNotNull(productDao.getById(100));
    }

    @Test
    @DisplayName("getById returns empty optional product for non-existing product")
    void givenTableContainingProducts_whenGetNotExistingProductById_thenEmptyOptionalProductReturned() {
        Optional<Product> optionalProduct = productDao.getById(100);
        assertTrue(optionalProduct.isEmpty());
    }

    @Test
    @DisplayName("getById returns actual product")
    void givenTable_whenGetById_thenActualProductReturned() {
        Optional<Product> actualOptionalProduct = productDao.getById(2);
        Product actualProduct = actualOptionalProduct.orElseThrow();

        assertEquals(2, actualProduct.getId());
        assertEquals("Ford", actualProduct.getName());
        assertEquals(74582.01, actualProduct.getPrice());
    }

    @Test
    @DisplayName("getById does not change previously existing products")
    void givenTable_whenGetById_thenPreviouslyExistingProductsDoesNotChange() {
        productDao.getById(2);

        List<Product> products = productDao.getAll();

        assertEquals(1, products.get(0).getId());
        assertEquals(3, products.get(2).getId());

        assertEquals("Mazda", products.get(0).getName());
        assertEquals("BMW", products.get(2).getName());

        assertEquals(43032.25, products.get(0).getPrice());
        assertEquals(52782.51, products.get(2).getPrice());

        assertEquals("first_product_description", products.get(0).getDescription());
        assertEquals("third_product_description", products.get(2).getDescription());
    }

    @Test
    @DisplayName("update does not change products' list size")
    void givenTable_whenUpdate_thenSizeOfProductsListDoesNotChange() {

        //prepare
        int initialListSize = productDao.getAll().size();

        Product product = Product.builder().
                id(2).
                name("Lexus").
                price(96054.39).
                creationDate(LocalDateTime.now()).
                description("description").
                build();

        //when
        productDao.update(product);

        //then
        int listSizeAfterUpdate = productDao.getAll().size();
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
                description("description").
                build();

        //when
        productDao.update(product);

        //then
        List<Product> products = productDao.getAll();

        assertEquals(1, products.get(0).getId());
        assertEquals(3, products.get(2).getId());

        assertEquals("Mazda", products.get(0).getName());
        assertEquals("BMW", products.get(2).getName());

        assertEquals(43032.25, products.get(0).getPrice());
        assertEquals(52782.51, products.get(2).getPrice());

        assertEquals("first_product_description", products.get(0).getDescription());
        assertEquals("third_product_description", products.get(2).getDescription());
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
                description("description").
                build();

        //when
        productDao.update(product);

        //then
        List<Product> products = productDao.getAll();

        assertEquals(2, products.get(1).getId());
        assertEquals("Lexus", products.get(1).getName());
        assertEquals(96054.39, products.get(1).getPrice());
        assertEquals("description", products.get(1).getDescription());
    }

    @Test
    @DisplayName("search returns not Null object if no products found")
    void givenTable_whenSearch_thenNotNullObjectReturned() {
        List<Product> products = productDao.search("some_text");
        assertNotNull(products);
    }

    @Test
    @DisplayName("search returns empty products list if no products found")
    void givenTable_whenSearch_thenEmptyListReturned() {
        List<Product> products = productDao.search("some_text");
        assertTrue(products.isEmpty());
    }

    @Test
    @DisplayName("search returns list of known size if products found")
    void givenTable_whenSearch_thenListOfKnownSizeReturned() {
        List<Product> products = productDao.search("description");
        assertEquals(3, products.size());
    }

    @Test
    @DisplayName("search returns list of found products")
    void givenTable_whenSearch_thenListOfFoundProductsReturned() {
        List<Product> products = productDao.search("description");

        assertEquals(1, products.get(0).getId());
        assertEquals(2, products.get(1).getId());
        assertEquals(3, products.get(2).getId());

        assertEquals("Mazda", products.get(0).getName());
        assertEquals("Ford", products.get(1).getName());
        assertEquals("BMW", products.get(2).getName());

        assertEquals(43032.25, products.get(0).getPrice());
        assertEquals(74582.01, products.get(1).getPrice());
        assertEquals(52782.51, products.get(2).getPrice());

        assertEquals("first_product_description", products.get(0).getDescription());
        assertEquals("second_product_description", products.get(1).getDescription());
        assertEquals("third_product_description", products.get(2).getDescription());
    }

    @Test
    @DisplayName("search does not change previously existing products")
    void givenTable_whenSearch_thenPreviouslyExistingProductsDoesNotChange() {
        productDao.search("Mazda");

        List<Product> productsAfterSearch = productDao.getAll();

        assertEquals("Mazda", productsAfterSearch.get(0).getName());
        assertEquals("Ford", productsAfterSearch.get(1).getName());
        assertEquals("BMW", productsAfterSearch.get(2).getName());

        assertEquals(43032.25, productsAfterSearch.get(0).getPrice());
        assertEquals(74582.01, productsAfterSearch.get(1).getPrice());
        assertEquals(52782.51, productsAfterSearch.get(2).getPrice());

        assertEquals("first_product_description", productsAfterSearch.get(0).getDescription());
        assertEquals("second_product_description", productsAfterSearch.get(1).getDescription());
        assertEquals("third_product_description", productsAfterSearch.get(2).getDescription());
    }
}