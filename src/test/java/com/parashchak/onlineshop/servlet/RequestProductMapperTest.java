package com.parashchak.onlineshop.servlet;

import com.parashchak.onlineshop.entity.Product;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;

import static com.parashchak.onlineshop.servlet.RequestProductMapper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RequestProductMapperTest {

    HttpServletRequest mockHttpServletRequest;

    @BeforeEach
    void init(){
        mockHttpServletRequest = mock(HttpServletRequest.class);
        when(mockHttpServletRequest.getParameter("name")).thenReturn("product");
        when(mockHttpServletRequest.getParameter("price")).thenReturn("50.00");
    }

    @Test
    @DisplayName("get not NULL Product instance from HTTP request data")
    void givenHttpRequest_whenProductMapperCalled_ThenNotNullProductReturned() {
        //prepare
        when(mockHttpServletRequest.getParameter("id")).thenReturn("100");
        //when
        Product actualProduct = mapProduct(mockHttpServletRequest);
        //then
        assertNotNull(actualProduct);
    }

    @Test
    @DisplayName("get Product instance with set fields from HTTP request data")
    void givenHttpRequest_whenProductMapperCalled_ThenProductWithSetFieldsReturned() {
        //prepare
        when(mockHttpServletRequest.getParameter("id")).thenReturn("100");
        //when
        Product actualProduct = mapProduct(mockHttpServletRequest);
        //then
        assertEquals(100, actualProduct.getId());
        assertEquals("product", actualProduct.getName());
        assertEquals(50.00, actualProduct.getPrice());
    }

    @Test
    @DisplayName("get not NULL Product instance from HTTP request data not containing id parameter")
    void givenHttpRequest_whenProductMapperCalledOnRequestNotContainingIdParameter_ThenNotNullProductReturned() {
        //prepare
        when(mockHttpServletRequest.getParameter("id")).thenReturn(null);
        //when
        Product actualProduct = mapProduct(mockHttpServletRequest);
        //then
        assertNotNull(actualProduct);
    }

    @Test
    @DisplayName("get Product instance with id=0 from HTTP request data not containing id parameter")
    void givenHttpRequest_whenProductMapperCalledOnRequestNotContainingIdParameter_ThenProductWithZeroIdReturned() {
        //prepare
        when(mockHttpServletRequest.getParameter("id")).thenReturn(null);
        //when
        Product actualProduct = mapProduct(mockHttpServletRequest);
        //then
        assertEquals(0, actualProduct.getId());
    }

    @Test
    @DisplayName("get Product instance with set fields (while id=0) from HTTP request data not containing id parameter")
    void givenHttpRequest_whenProductMapperCalledOnRequestNotContainingIdParameter_ThenProductWithSetFieldsReturned() {
        //prepare
        when(mockHttpServletRequest.getParameter("id")).thenReturn(null);
        //when
        Product actualProduct = mapProduct(mockHttpServletRequest);
        //then
        assertEquals("product", actualProduct.getName());
        assertEquals(50.00, actualProduct.getPrice());
    }
}