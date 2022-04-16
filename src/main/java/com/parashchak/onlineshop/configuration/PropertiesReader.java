package com.parashchak.onlineshop.configuration;

import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

public class PropertiesReader {

    public static Properties getConfigProperties() {
        Properties configProperties;
        Properties appProperties = readAppPropertiesFile("configs/application.properties");
        Properties environmentProperties = new Properties();

        try {
            environmentProperties = readEnvironmentProperties();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        configProperties = mergeProperties(appProperties, environmentProperties);
        return configProperties;
    }

    private static Properties mergeProperties(Properties appProperties, Properties environmentProperties) {
        if (environmentProperties.isEmpty()) {
            return appProperties;
        }
        return environmentProperties;
    }


    private static Properties readAppPropertiesFile(String appPropertiesFilePath) {
        Properties appProperties = new Properties();
        try (InputStream in = PropertiesReader.class.getClassLoader().getResourceAsStream(appPropertiesFilePath)) {
            appProperties.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Cannot read application properties file {" + appPropertiesFilePath + " }", e);
        }
        return appProperties;
    }

    private static Properties readEnvironmentProperties() {
        Properties environmentProperties = new Properties();
        try {
            String env = System.getenv("DATABASE_URL");
            String serverPort = System.getenv("PORT");

            URI dbUri = new URI(env);
            String dBUser = dbUri.getUserInfo().split(":")[0];
            String dBPassword = dbUri.getUserInfo().split(":")[1];
            String dBHost = dbUri.getHost();
            int dBPort = dbUri.getPort();
            String dBPath = dbUri.getPath();
            String dbUrl = "jdbc:postgresql://" + dBHost + ':' + dBPort + dBPath;
            environmentProperties.setProperty("db.url", dbUrl);
            environmentProperties.setProperty("db.user", dBUser);
            environmentProperties.setProperty("db.password", dBPassword);

            environmentProperties.setProperty("server.port", String.valueOf(serverPort));

        } catch (Exception e) {
            throw new RuntimeException("Cannot read environment properties", e);
        }
        return environmentProperties;
    }
}