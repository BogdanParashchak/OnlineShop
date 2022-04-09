package com.parashchak.onlineshop.servlet;

import com.parashchak.onlineshop.entity.Product;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.parashchak.onlineshop.servlet.RequestProductMapper.mapProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestProductMapperTest {

    @Test
    @DisplayName("mapProduct gets Product instance from HTTP request data")
    void givenHttpRequest_whenProductMapperCalled_ThenProductReturned() {
        //prepare
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
        when(mockHttpServletRequest.getParameter("name")).thenReturn("product");
        when(mockHttpServletRequest.getParameter("price")).thenReturn("50.00");

        //when
        Product actualProduct = mapProduct(mockHttpServletRequest);

        //then
        assertNotNull(actualProduct);
        assertEquals("product", actualProduct.getName());
        assertEquals(50.00, actualProduct.getPrice());
    }
}