package com.parashchak.onlineshop.dao;

import com.parashchak.onlineshop.entity.Product;
import org.junit.jupiter.api.*;

import java.sql.*;

import static com.parashchak.onlineshop.dao.ResultSetProductMapper.mapProduct;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResultSetProductMapperTest {

    ResultSet mockResultSet;

    @BeforeEach
    void init() throws SQLException {
        mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("product");
        when(mockResultSet.getDouble("price")).thenReturn(50.00);
    }

    @Test
    @DisplayName("get not NULL Product instance from ResultSet row")
    void givenResultSet_whenProductMapperCalled_ThenProductNotNullReturned() throws SQLException {
        //when
        Product actualProduct = mapProduct(mockResultSet);
        //then
        assertNotNull(actualProduct);
    }

    @Test
    @DisplayName("get Product instance with set fields from ResultSet row")
    void givenResultSet_whenProductMapperCalled_ThenProductWithSetFieldsReturned() throws SQLException {
        //when
        Product actualProduct = mapProduct(mockResultSet);
        //then
        assertEquals(1, actualProduct.getId());
        assertEquals("product", actualProduct.getName());
        assertEquals(50.00, actualProduct.getPrice());
    }
}