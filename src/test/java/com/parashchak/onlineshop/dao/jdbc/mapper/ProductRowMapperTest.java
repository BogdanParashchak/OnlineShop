package com.parashchak.onlineshop.dao.jdbc.mapper;

import com.parashchak.onlineshop.dao.jdbc.ProductRowMapper;
import com.parashchak.onlineshop.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductRowMapperTest {

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
        //prepare
        ProductRowMapper productRowMapper = new ProductRowMapper();
        //when
        Product actualProduct = productRowMapper.mapRow(mockResultSet);
        //then
        assertNotNull(actualProduct);
    }

    @Test
    @DisplayName("get Product instance with set fields from ResultSet row")
    void givenResultSet_whenProductMapperCalled_ThenProductWithSetFieldsReturned() throws SQLException {
        //prepare
        ProductRowMapper productRowMapper = new ProductRowMapper();
        //when
        Product actualProduct = productRowMapper.mapRow(mockResultSet);
        //then
        assertEquals(1, actualProduct.getId());
        assertEquals("product", actualProduct.getName());
        assertEquals(50.00, actualProduct.getPrice());
    }
}