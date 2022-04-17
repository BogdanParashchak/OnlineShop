package com.parashchak.onlineshop.configuration;

import java.util.Properties;

public class PortConfigurator {
    public static int getPort(Properties configProperties) {
        String port;
        try {
            port = configProperties.getProperty("server.port");
        } catch (Exception e) {
            throw new RuntimeException("Cannot read port number property", e);
        }
        return Integer.parseInt(port);
    }
}