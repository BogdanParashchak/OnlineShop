package com.parashchak.onlineshop.configuration;

import java.util.Properties;

public class PortConfigurator {
    public static int getPort(Properties configProperties){
        String port = configProperties.getProperty("server.port");
        return Integer.parseInt(port);
    }
}