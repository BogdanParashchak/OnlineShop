package com.parashchak.onlineshop.configuration;

import java.util.Properties;

public class PortConfigurator {
    public static int getPort(Properties configProperties) {

        if (!configProperties.containsKey("server.port")) {
            throw new RuntimeException("Cannot read port number property");
        }
        return Integer.parseInt(configProperties.getProperty("server.port"));
    }
}