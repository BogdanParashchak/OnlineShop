package com.parashchak.onlineshop.configuration;

import org.junit.jupiter.api.*;

import java.util.Properties;

import static com.parashchak.onlineshop.configuration.PortConfigurator.getPort;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PortConfiguratorTest {

    @Test
    @DisplayName("get port number from properties")
    void givenProperties_WhenGetPort_ThenPortNumberReturned() {
        Properties appProperties = new Properties();
        appProperties.setProperty("server.port", "3000");
        int actualPort = getPort(appProperties);
        assertEquals(3000, actualPort);
    }

    @Test
    @DisplayName("RuntimeException thrown when port number property not present in properties")
    void givenPropertiesWherePortNumberNotPresent_WhenGetPort_ThenRuntimeExceptionThrown() {
        Properties appProperties = new Properties();
        Assertions.assertThrows(RuntimeException.class, () -> getPort(appProperties));
    }

    @Test
    @DisplayName("RuntimeException thrown when port number is incorrect")
    void givenPropertiesWherePortNumberIsIncorrect_WhenGetPort_ThenRuntimeExceptionThrown() {
        Properties appProperties = new Properties();
        appProperties.setProperty("server.port", "incorrect_port_number");
        Assertions.assertThrows(RuntimeException.class, () -> getPort(appProperties));
    }
}