package com.parashchak.onlineshop.dao.jdbc;

import com.parashchak.onlineshop.dao.UserDao;
import com.parashchak.onlineshop.entity.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class JdbcUserDaoTest {

    private final DataSource mockDatasource = mock(DataSource.class);
    private final Connection mockConnection = mock(Connection.class);
    private final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
    private final ResultSet mockResultSet = mock(ResultSet.class);

    private final UserDao userDao = new JdbcUserDao(mockDatasource);

    @BeforeEach
    @SneakyThrows
    void setUp() {
        when(mockDatasource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("login")).thenReturn("aLogin");
        when(mockResultSet.getString("password")).thenReturn("aPassword");
        when(mockResultSet.getString("salt")).thenReturn("aSalt");
    }

    @Test
    @SneakyThrows
    @DisplayName("verify findByLogin logic executed")
    void whenGet_thenGetLogicExecuted() {
        userDao.findByLogin("user");

        verify(mockDatasource).getConnection();
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt("id");
        verify(mockResultSet, times(1)).getString("login");
        verify(mockResultSet, times(1)).getString("password");
        verify(mockResultSet, times(1)).getString("salt");
        verify(mockResultSet, times(1)).close();
        verify(mockPreparedStatement, times(1)).close();
        verify(mockConnection, times(1)).close();
    }

    @Test
    @SneakyThrows
    @DisplayName("findByLogin returns User instance")
    void whenGet_thenReturnsUserInstance() {
        Optional<User> optionalUser = userDao.findByLogin("user");
        User user = optionalUser.orElseThrow();

        assertEquals(1, user.getId());
        assertEquals("aLogin", user.getLogin());
        assertEquals("aPassword", user.getPassword());
        assertEquals("aSalt", user.getSalt());
    }

    @Test
    @SneakyThrows
    @DisplayName("findByLogin catches Exception and throws new RuntimeException")
    void whenGetAll_thenRuntimeExceptionThrown() {
        when(mockDatasource.getConnection()).thenThrow(new SQLException());
        Assertions.assertThrows(RuntimeException.class, () -> userDao.findByLogin("user"));
    }
}