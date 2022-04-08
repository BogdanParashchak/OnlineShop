package com.parashchak.onlineshop.dao;

import com.parashchak.onlineshop.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.parashchak.onlineshop.dao.ProductMapper.mapProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductMapperTest {

    @Test
    @DisplayName("mapProduct gets Product instance from ResultSet row")
    void givenResultSet_whenProductMapperCalled_ThenProductReturned() throws SQLException {

        //prepare
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("product");
        when(mockResultSet.getDouble("price")).thenReturn(50.00);

        //when
        Product actualProduct = mapProduct(mockResultSet);

        //then
        assertNotNull(actualProduct);
        assertEquals(1, actualProduct.getId());
        assertEquals("product", actualProduct.getName());
        assertEquals(50.00, actualProduct.getPrice());
    }
}