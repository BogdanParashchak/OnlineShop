package com.parashchak.onlineshop.configuration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.*;

import java.io.*;
import java.util.*;

import static com.parashchak.onlineshop.configuration.PropertiesReader.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SystemStubsExtension.class)
public class PropertiesReaderTest {

    @SystemStub
    private EnvironmentVariables environmentVariables;

    @Test
    @DisplayName("When merge properties from file and empty properties from environment then properties from file returned")
    void givenPropertiesFromFileAndEmptyPropertiesFromEnvironment_WhenMergeProperties_ThenPropertiesFromFileReturned() {

        //prepare
        Properties mockPropertiesFromFile = mock(Properties.class);
        Properties mockPropertiesFromEnvironment = mock(Properties.class);
        when(mockPropertiesFromEnvironment.isEmpty()).thenReturn(true);

        //when
        Properties actualReturnedProperties = mergeProperties(mockPropertiesFromFile, mockPropertiesFromEnvironment);

        //then
        assertEquals(mockPropertiesFromFile, actualReturnedProperties);
    }

    @Test
    @DisplayName("When merge properties from file and not empty properties from environment then properties from environment returned")
    void givenEmptyPropertiesFromFileAndPropertiesFromEnvironment_WhenMergeProperties_ThenPropertiesFromEnvironmentReturned() {

        //prepare
        Properties mockPropertiesFromFile = mock(Properties.class);
        Properties mockPropertiesFromEnvironment = mock(Properties.class);
        when(mockPropertiesFromEnvironment.isEmpty()).thenReturn(false);

        //when
        Properties actualReturnedProperties = mergeProperties(mockPropertiesFromFile, mockPropertiesFromEnvironment);

        //then
        assertEquals(mockPropertiesFromEnvironment, actualReturnedProperties);
    }

    @Test
    @DisplayName("get Properties instance from input stream")
    void givenInputStream_WhenReadAppPropertiesFile_ThenPropertiesInstanceReturned() {
        //prepare
        String url = "db.url=jdbc:postgresql://localhost:5432/test_db";
        String user = "db.user=test_user";
        String password = "db.password=test_password";
        String port = "server.port=3000";
        String timeToLive = "session.timeToLive=3600";
        StringJoiner stringJoiner = new StringJoiner("\n")
                .add(url)
                .add(user)
                .add(password)
                .add(port)
                .add(timeToLive);
        InputStream mockInputStream = new ByteArrayInputStream(stringJoiner.toString().getBytes());

        //when
        Properties properties = readLocalProperties(mockInputStream);

        //then
        assertEquals("jdbc:postgresql://localhost:5432/test_db", properties.getProperty("db.url"));
        assertEquals("test_user", properties.getProperty("db.user"));
        assertEquals("test_password", properties.getProperty("db.password"));
        assertEquals("3000", properties.getProperty("server.port"));
        assertEquals("3600", properties.getProperty("session.timeToLive"));
    }


    @Test
    @DisplayName("get Properties instance from DATABASE_URL and PORT environment variables")
    void givenEnvironmentVariables_WhenReadEnvironmentProperties_ThenPropertiesInstanceReturned() {
        //prepare
        environmentVariables.set("DATABASE_URL", "postgresql://test_user:test_password@localhost:5432/test_db");
        environmentVariables.set("PORT", "3000");
        environmentVariables.set("TIME_TO_LIVE", "3600");

        //when
        Properties properties = readEnvironmentProperties();

        //then
        assertEquals("jdbc:postgresql://localhost:5432/test_db", properties.getProperty("db.url"));
        assertEquals("test_user", properties.getProperty("db.user"));
        assertEquals("test_password", properties.getProperty("db.password"));
        assertEquals("3000", properties.getProperty("server.port"));
        assertEquals("3600", properties.getProperty("session.timeToLive"));
    }

    @Test
    @DisplayName("RuntimeException thrown when reading incorrect DATABASE_URL environment variable")
    void givenIncorrectDatabaseUrlEnvironmentVariable_WhenReadEnvironmentProperties_ThenExceptionThrown() {
        environmentVariables.set("DATABASE_URL", "incorrect_variable_value");
        environmentVariables.set("PORT", "3000");
        environmentVariables.set("TIME_TO_LIVE", "3600");

        assertThrows(RuntimeException.class, PropertiesReader::readEnvironmentProperties);
    }

    @Test
    @DisplayName("RuntimeException thrown when reading Null DATABASE_URL environment variable")
    void givenDatabaseUrlEnvironmentVariableIsNull_WhenReadEnvironmentProperties_ThenExceptionThrown() {
        environmentVariables.set("DATABASE_URL", null);
        environmentVariables.set("PORT", "3000");
        environmentVariables.set("TIME_TO_LIVE", "3600");

        assertThrows(RuntimeException.class, PropertiesReader::readEnvironmentProperties);
    }

    @Test
    @DisplayName("RuntimeException thrown when reading Null PORT environment variable")
    void givenPortEnvironmentVariableIsNull_WhenReadEnvironmentProperties_ThenExceptionThrown() {
        environmentVariables.set("DATABASE_URL", "postgresql://test_user:test_password@localhost:5432/test_db");
        environmentVariables.set("PORT", null);
        environmentVariables.set("TIME_TO_LIVE", "3600");

        assertThrows(RuntimeException.class, PropertiesReader::readEnvironmentProperties);
    }

    @Test
    @DisplayName("RuntimeException thrown when reading Null TIME_TO_LIVE environment variable")
    void givenTimeToLiveVariableIsNull_WhenReadEnvironmentProperties_ThenExceptionThrown() {
        environmentVariables.set("DATABASE_URL", "postgresql://test_user:test_password@localhost:5432/test_db");
        environmentVariables.set("PORT", 3000);
        environmentVariables.set("TIME_TO_LIVE", null);

        assertThrows(RuntimeException.class, PropertiesReader::readEnvironmentProperties);
    }
}