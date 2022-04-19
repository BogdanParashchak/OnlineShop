package com.parashchak.onlineshop.dao.jdbc;

import com.zaxxer.hikari.HikariConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JdbcConnectionFactoryTest {

    @Test
    @DisplayName("when set data source configs then not NUll HikariConfig instance returned")
    void givenProperties_whenSetDataSourceConfigs_thenNotNullDataSourceConfigsInstanceReturned() {

        //prepare
        Properties configProperties = new Properties();
        configProperties.setProperty("db.url", "jdbc:postgresql://localhost:5432/online_shop");
        configProperties.setProperty("db.user", "app");
        configProperties.setProperty("db.password", "app");
        JdbcConnectionFactory jdbcConnectionFactory = new JdbcConnectionFactory(configProperties);

        //when
        HikariConfig actualHikariConfig = jdbcConnectionFactory.setDataSourceConfigs(configProperties);

        //then
        assertNotNull(actualHikariConfig);
    }

    @Test
    @DisplayName("when set data source configs then HikariConfig instance with set fields returned")
    void givenProperties_whenSetDataSourceConfigs_thenDataSourceConfigsSet() {

        //prepare
        Properties configProperties = new Properties();
        configProperties.setProperty("db.url", "jdbc:postgresql://localhost:5432/online_shop");
        configProperties.setProperty("db.user", "app");
        configProperties.setProperty("db.password", "app");
        JdbcConnectionFactory jdbcConnectionFactory = new JdbcConnectionFactory(configProperties);

        //when
        HikariConfig actualHikariConfig = jdbcConnectionFactory.setDataSourceConfigs(configProperties);

        //then
        assertEquals("jdbc:postgresql://localhost:5432/online_shop", actualHikariConfig.getJdbcUrl());
        assertEquals("app", actualHikariConfig.getUsername());
        assertEquals("app", actualHikariConfig.getPassword());
    }
}