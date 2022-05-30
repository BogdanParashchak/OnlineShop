package com.parashchak.onlineshop.dao.jdbc;

import com.parashchak.onlineshop.dao.ProductDao;
import com.parashchak.onlineshop.entity.Product;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class JdbcProductDaoTest {

    private final DataSource mockDataSource = mock(DataSource.class);
    private final Connection mockConnection = mock(Connection.class);
    private final Statement mockStatement = mock(Statement.class);
    private final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
    private final ResultSet mockResultSet = mock(ResultSet.class);

    private final ProductDao productDao = new JdbcProductDao(mockDataSource);

    @BeforeEach
    @SneakyThrows
    void setUp() {
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStatement.getResultSet()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("name")).thenReturn("firstProduct", "secondProduct");
        when(mockResultSet.getDouble("price")).thenReturn(100.0, 200.0);
        when(mockResultSet.getTimestamp("creation_date")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(mockResultSet.getString("description")).thenReturn("description");
    }

    @Test
    @SneakyThrows
    @DisplayName("verify getAll logic executed")
    void whenGetAll_thenGetAllLogicExecuted() {
        productDao.getAll();

        verify(mockDataSource).getConnection();
        verify(mockStatement).executeQuery(anyString());
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(2)).getInt("id");
        verify(mockResultSet, times(2)).getString("name");
        verify(mockResultSet, times(2)).getDouble("price");
        verify(mockResultSet, times(2)).getTimestamp("creation_date");
        verify(mockResultSet, times(2)).getString("description");
    }

    @Test
    @SneakyThrows
    @DisplayName("getAll catches Exception and throws new RuntimeException")
    void whenGetAll_thenRuntimeExceptionThrown() {
        when(mockDataSource.getConnection()).thenThrow(new SQLException());
        Assertions.assertThrows(RuntimeException.class, productDao::getAll);
    }

    @Test
    @SneakyThrows
    @DisplayName("verify add logic executed")
    void whenAdd_thenAddLogicExecuted() {
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
        verify(mockDataSource).getConnection();
        verify(mockPreparedStatement).setString(1, "Lexus");
        verify(mockPreparedStatement).setDouble(2, 96054.39);
        verify(mockPreparedStatement).setTimestamp(anyInt(), any(Timestamp.class));
        verify(mockPreparedStatement).setString(4, "description");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @SneakyThrows
    @DisplayName("add catches Exception and throws new RuntimeException")
    void whenAdd_thenRuntimeExceptionThrown() {
        Product product = Product.builder().
                name("Lexus").
                price(96054.39).
                creationDate(LocalDateTime.now()).
                description("description").
                build();
        when(mockDataSource.getConnection()).thenThrow(new SQLException());

        Assertions.assertThrows(RuntimeException.class, () -> productDao.add(product));
    }

    @Test
    @SneakyThrows
    @DisplayName("verify delete logic executed")
    void whenDelete_thenDeleteLogicExecuted() {
        productDao.delete(1);

        //then
        verify(mockDataSource).getConnection();
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @SneakyThrows
    @DisplayName("delete catches Exception and throws new RuntimeException")
    void whenDelete_thenRuntimeExceptionThrown() {
        when(mockDataSource.getConnection()).thenThrow(new SQLException());

        Assertions.assertThrows(RuntimeException.class, () -> productDao.delete(1));
    }

    @Test
    @SneakyThrows
    @DisplayName("verify getById logic executed")
    void whenGetByIdl_thenGetByIdLogicExecuted() {
        productDao.getById(1);

        verify(mockDataSource).getConnection();
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt("id");
        verify(mockResultSet, times(1)).getString("name");
        verify(mockResultSet, times(1)).getDouble("price");
        verify(mockResultSet, times(1)).getTimestamp("creation_date");
        verify(mockResultSet, times(1)).getString("description");
    }

    @Test
    @SneakyThrows
    @DisplayName("getById returns not Null optional product if product exists in table")
    void productExistingInTable_whenGetById_thenNotNullOptionalProductReturned() {
        Optional<Product> optionalProduct = productDao.getById(1);
        assertNotNull(optionalProduct);
    }

    @Test
    @DisplayName("get returns not empty optional object if user exists in table")
    void productExistingInTable_whenGetById_thenNotEmptyOptionalProductReturned() {
        Optional<Product> optionalProduct = productDao.getById(1);
        assertFalse(optionalProduct.isEmpty());
    }

    @Test
    @SneakyThrows
    @DisplayName("getById catches Exception and throws new RuntimeException")
    void whenGetById_thenRuntimeExceptionThrown() {
        when(mockDataSource.getConnection()).thenThrow(new SQLException());

        Assertions.assertThrows(RuntimeException.class, () -> productDao.getById(1));
    }

    @Test
    @SneakyThrows
    @DisplayName("verify update logic executed")
    void whenUpdate_thenUpdateLogicExecuted() {

        Product product = Product.builder().
                id(2).
                name("Lexus").
                price(96054.39).
                creationDate(LocalDateTime.now()).
                description("description").
                build();

        productDao.update(product);

        verify(mockDataSource).getConnection();
        verify(mockPreparedStatement).setString(1, "Lexus");
        verify(mockPreparedStatement).setDouble(2, 96054.39);
        verify(mockPreparedStatement).setString(3, "description");
        verify(mockPreparedStatement).setInt(4, 2);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @SneakyThrows
    @DisplayName("update catches SQLException and throws new RuntimeException")
    void whenUpdate_thenRuntimeExceptionThrown() {
        Product product = Product.builder().
                id(2).
                name("Lexus").
                price(96054.39).
                creationDate(LocalDateTime.now()).
                description("description").
                build();
        when(mockDataSource.getConnection()).thenThrow(new SQLException());

        Assertions.assertThrows(RuntimeException.class, () -> productDao.update(product));
    }

    @Test
    @SneakyThrows
    @DisplayName("verify search logic executed")
    void whenSearch_thenSearchLogicExecuted() {
        productDao.search("description");

        verify(mockDataSource).getConnection();
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(2)).getInt("id");
        verify(mockResultSet, times(2)).getString("name");
        verify(mockResultSet, times(2)).getDouble("price");
        verify(mockResultSet, times(2)).getTimestamp("creation_date");
        verify(mockResultSet, times(2)).getString("description");
    }

    @Test
    @SneakyThrows
    @DisplayName("search catches SQLException and throws new RuntimeException")
    void whenSearch_thenRuntimeExceptionThrown() {
        when(mockDataSource.getConnection()).thenThrow(new SQLException());

        Assertions.assertThrows(RuntimeException.class, () -> productDao.search("description"));
    }
}