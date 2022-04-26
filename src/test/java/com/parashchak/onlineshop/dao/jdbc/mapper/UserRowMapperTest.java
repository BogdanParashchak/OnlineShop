package com.parashchak.onlineshop.dao.jdbc.mapper;

import com.parashchak.onlineshop.entity.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRowMapperTest {

    ResultSet mockResultSet;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("login")).thenReturn("login");
        when(mockResultSet.getString("password")).thenReturn("password");
        when(mockResultSet.getString("salt")).thenReturn("salt");
    }

    @Test
    @SneakyThrows
    @DisplayName("get not NULL User instance from ResultSet row")
    void givenResultSet_whenUserMapperCalled_thenNotNullUserReturned() {
        //prepare
        UserRowMapper userRowMapper = new UserRowMapper();
        //when
        User actualUser = userRowMapper.mapRow(mockResultSet);
        //then
        assertNotNull(actualUser);
    }

    @Test
    @SneakyThrows
    @DisplayName("get User instance with set fields from ResultSet row")
    void givenResultSet_whenUserMapperCalled_thenUserWithSetFieldsReturned() {
        //prepare
        UserRowMapper userRowMapper = new UserRowMapper();
        //when
        User actualUser = userRowMapper.mapRow(mockResultSet);
        //then
        assertEquals(1, actualUser.getId());
        assertEquals("login", actualUser.getLogin());
        assertEquals("password", actualUser.getPassword());
        assertEquals("salt", actualUser.getSalt());
    }
}