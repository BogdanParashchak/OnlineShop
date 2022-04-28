package com.parashchak.onlineshop.dao.jdbc;

import com.parashchak.onlineshop.entity.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JdbcUserDaoITest {

    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "app";
    private static final String PASSWORD = "app";

    private final Properties properties = new Properties();
    JdbcConnectionFactory jdbcConnectionFactory;
    JdbcUserDao jdbcUserDao;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        properties.setProperty("db.url", URL);
        properties.setProperty("db.user", USER);
        properties.setProperty("db.password", PASSWORD);

        jdbcConnectionFactory = new JdbcConnectionFactory(properties);
        jdbcUserDao = new JdbcUserDao(jdbcConnectionFactory);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS accounts;");
            statement.execute("CREATE TABLE accounts (id SERIAL PRIMARY KEY, login VARCHAR(10) UNIQUE NOT NULL, password VARCHAR(256) NOT NULL, salt VARCHAR(256) NOT NULL);");
            statement.execute("INSERT INTO accounts (login, password, salt) VALUES ('user', 'user-password', 'user-salt');");
            statement.execute("INSERT INTO accounts (login, password, salt) VALUES ('admin', 'admin-password', 'admin-salt');");
        }
    }

    @Test
    @DisplayName("get returns not Null optional object if user exists in table")
    void givenTable_whenGetExistingUser_thenNotNullOptionalObjectReturned() {
        Optional<User> optionalUser = jdbcUserDao.get("user");
        assertNotNull(optionalUser);
    }

    @Test
    @DisplayName("get returns not empty optional object if user exists in table")
    void givenTable_whenGetExistingUser_thenNotEmptyOptionalObjectReturned() {
        Optional<User> optionalUser = jdbcUserDao.get("user");
        assertFalse(optionalUser.isEmpty());
    }

    @Test
    @DisplayName("get returns not Null optional object if user does not exist in table")
    void givenTable_whenGetNotExistingUser_thenNotNullOptionalObjectReturned() {
        Optional<User> optionalUser = jdbcUserDao.get("non-existing-user");
        assertNotNull(optionalUser);
    }

    @Test
    @DisplayName("get returns empty optional object if user does not exist in table")
    void givenTable_whenGetNotExistingUser_thenEmptyOptionalObjectReturned() {
        Optional<User> optionalUser = jdbcUserDao.get("non-existing-user");
        assertTrue(optionalUser.isEmpty());
    }

    @Test
    @DisplayName("get returns actual user")
    void givenTable_whenGet_thenActualUserReturned() {
        User user = jdbcUserDao.get("user").orElseThrow();
        User admin = jdbcUserDao.get("admin").orElseThrow();

        assertEquals("user", user.getLogin());
        assertEquals("user-password", user.getPassword());
        assertEquals("user-salt", user.getSalt());

        assertEquals("admin", admin.getLogin());
        assertEquals("admin-password", admin.getPassword());
        assertEquals("admin-salt", admin.getSalt());
    }

    @Test
    @DisplayName("get does not change user in table")
    void givenTable_whenGet_thenUserIsNotChanged() {
        User firstUser = jdbcUserDao.get("user").orElseThrow();
        User secondUser = jdbcUserDao.get("user").orElseThrow();
        assertEquals(firstUser, secondUser);
    }
}