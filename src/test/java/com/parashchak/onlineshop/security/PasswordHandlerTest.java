package com.parashchak.onlineshop.security;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHandlerTest {

    @Test
    @DisplayName("getKey returns byte array")
    void givenPasswordAndSalt_whenGetKey_thenByteArrayReturned() {

        //prepare
        String password = "password";
        String salt = "salt";

        //when
        byte[] key = new PasswordHandler().getKey(password, salt);

        //then
        assertNotNull(key);
        assertTrue(key.length > 0);
    }

    @Test
    @DisplayName("getKey catches Exception and throws new RuntimeException")
    void testF() {

        //prepare
        String password = "password";
        String salt = "";

        //then
        Assertions.assertThrows(RuntimeException.class, () -> new PasswordHandler().getKey(password, salt));
    }

    @Test
    @DisplayName("generateEncryptedPassword return not null String")
    void test() {
        //prepare
        String password = "password";
        String salt = "salt";

        //when
        String encryptedPassword = new PasswordHandler().generateEncryptedPassword(password, salt);

        //then
        assertNotNull(encryptedPassword);
    }
}