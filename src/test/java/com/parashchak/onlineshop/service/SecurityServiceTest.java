package com.parashchak.onlineshop.service;

import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityServiceTest {

    private final HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final SecurityService securityService = new SecurityService();

    @Test
    @DisplayName("validateSession returns false on request without cookies")
    void givenRequestWithoutCookies_whenValidateSession_thenReturnFalse() {

        //prepare
        when(mockHttpServletRequest.getCookies()).thenReturn(null);

        //when
        boolean isValid = securityService.validateSession(mockHttpServletRequest);

        //then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("validateSession triggers getName on every cookie from request containing cookies")
    void givenRequestContainingCookies_whenValidateSession_thenGetNameOnEveryCookie() {

        //prepare
        Cookie mockFirstCookie = mock(Cookie.class);
        Cookie mockSecondCookie = mock(Cookie.class);
        Cookie[] mockCookies = new Cookie[]{mockFirstCookie, mockSecondCookie};
        when(mockHttpServletRequest.getCookies()).thenReturn(mockCookies);

        //when
        securityService.validateSession(mockHttpServletRequest);

        //then
        verify(mockFirstCookie, times(1)).getName();
        verify(mockSecondCookie, times(1)).getName();
    }

    @Test
    @DisplayName("validateSession triggers getValue only on cookie which name is 'user-token'")
    void givenRequestContainingCookieWhichNameIsUserToken_whenValidateSession_thenGetValueOfThatCookie() {

        //prepare
        Cookie mockFirstCookie = mock(Cookie.class);
        Cookie mockSecondCookie = mock(Cookie.class);
        Cookie[] mockCookies = new Cookie[]{mockFirstCookie, mockSecondCookie};

        when(mockFirstCookie.getName()).thenReturn("user-token");
        when(mockSecondCookie.getName()).thenReturn("second cookie");
        when(mockHttpServletRequest.getCookies()).thenReturn(mockCookies);

        //when
        securityService.validateSession(mockHttpServletRequest);

        //then
        verify(mockFirstCookie, times(1)).getValue();
        verify(mockSecondCookie, times(0)).getValue();
    }

    @Test
    @DisplayName("validateSession returns true on cookie which name is 'user-token' and which value is found in session list")
    void givenRequest_whenValidateSession_thenReturnTrue() {

        //prepare
        securityService.getSessionList().add("session");

        Cookie mockFirstCookie = new Cookie("user-token", "session");
        Cookie mockSecondCookie = new Cookie("second-cookie", "second value");
        Cookie[] mockCookies = new Cookie[]{mockFirstCookie, mockSecondCookie};
        when(mockHttpServletRequest.getCookies()).thenReturn(mockCookies);

        //when
        boolean isValid = securityService.validateSession(mockHttpServletRequest);

        //then
        assertTrue(isValid);
    }

    @Test
    @DisplayName("createSession increases session list by one")
    void givenResponse_whenCreateSession_thenSessionListIncreases() {

        //prepare
        List<String> sessions = securityService.getSessionList();
        int initialSessionListSize = sessions.size();

        //when
        securityService.createSession(response);
        int sessionListSizeAfterSessionCreation = sessions.size();

        //then
        assertEquals(initialSessionListSize + 1, sessionListSizeAfterSessionCreation);
    }

    @Test
    @DisplayName("createSession adds session to session list")
    void givenResponse_whenCreateSession_thenSessionCreated() {

        //when
        securityService.createSession(response);

        //then
        List<String> sessions = securityService.getSessionList();
        String lastSession = sessions.get(sessions.size() - 1);
        assertEquals(36, lastSession.length());
    }

    @Test
    @DisplayName("createSession adds cookie to response")
    void givenResponse_whenCreateSession_thenCookieAddedToResponse() {

        //when
        securityService.createSession(response);

        //then
        verify(response).addCookie(any(Cookie.class));
    }
}