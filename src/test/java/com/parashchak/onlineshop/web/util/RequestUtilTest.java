package com.parashchak.onlineshop.web.util;

import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RequestUtilTest {

    private final HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);

    @Test
    @DisplayName("getUserToken returns empty user token on request without cookies")
    void givenRequestWithoutCookies_whenGetUserToken_thenReturnFalse() {

        //prepare
        when(mockHttpServletRequest.getCookies()).thenReturn(null);

        //when
        Optional<String> userToken = RequestUtil.getUserToken(mockHttpServletRequest);

        //then
        assertNotNull(userToken);
        assertTrue(userToken.isEmpty());
    }

    @Test
    @DisplayName("getUserToken triggers getName on every cookie from request containing cookies")
    void givenRequestContainingCookies_whenGetUserToken_thenGetNameOnEveryCookie() {

        //prepare
        Cookie mockFirstCookie = mock(Cookie.class);
        Cookie mockSecondCookie = mock(Cookie.class);
        Cookie[] mockCookies = new Cookie[]{mockFirstCookie, mockSecondCookie};
        when(mockHttpServletRequest.getCookies()).thenReturn(mockCookies);

        //when
        RequestUtil.getUserToken(mockHttpServletRequest);

        //then
        verify(mockFirstCookie, times(1)).getName();
        verify(mockSecondCookie, times(1)).getName();
    }

    @Test
    @DisplayName("getUserToken triggers getValue only on cookie which name is 'user-token'")
    void givenRequestContainingCookieWhichNameIsUserToken_whenGetUserToken_thenGetValueOfThatCookie() {

        //prepare
        Cookie mockFirstCookie = mock(Cookie.class);
        Cookie mockSecondCookie = mock(Cookie.class);
        Cookie[] mockCookies = new Cookie[]{mockFirstCookie, mockSecondCookie};

        when(mockFirstCookie.getName()).thenReturn("user-token");
        when(mockFirstCookie.getValue()).thenReturn("cookie value");

        when(mockSecondCookie.getName()).thenReturn("second cookie");
        when(mockHttpServletRequest.getCookies()).thenReturn(mockCookies);

        //when
        RequestUtil.getUserToken(mockHttpServletRequest);

        //then
        verify(mockFirstCookie, times(1)).getValue();
        verify(mockSecondCookie, times(0)).getValue();
    }
}