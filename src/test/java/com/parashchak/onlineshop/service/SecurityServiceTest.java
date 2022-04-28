package com.parashchak.onlineshop.service;

import com.parashchak.onlineshop.security.SecurityService;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityServiceTest {

    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final SecurityService securityService = new SecurityService();

    @Test
    @DisplayName("validateUserToken returns true on token which value is found in session list")
    void givenToken_whenValidateUserToken_thenReturnTrue() {

        //prepare
        securityService.getSessionList().add("session");
        Optional<String> userToken = Optional.of("session");

        //when
        boolean isValid = securityService.validateUserToken(userToken);

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