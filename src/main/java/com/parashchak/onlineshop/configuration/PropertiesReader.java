package com.parashchak.onlineshop.configuration;

import lombok.RequiredArgsConstructor;
import org.assertj.core.util.VisibleForTesting;

import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

@RequiredArgsConstructor
public class PropertiesReader {

    private static final String LOCAL_PROPERTIES_FILE_PATH = "configs/application.properties";

    public static Properties getAppProperties() {
        Properties localProperties = readLocalProperties();
        Properties environmentProperties = new Properties();

        try {
            environmentProperties = readEnvironmentProperties();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return mergeProperties(localProperties, environmentProperties);
    }


    @VisibleForTesting
    static Properties mergeProperties(Properties appProperties, Properties environmentProperties) {
        if (environmentProperties.isEmpty()) {
            return appProperties;
        }
        return environmentProperties;
    }

    @VisibleForTesting
    static Properties readLocalProperties() {
        return readLocalProperties(PropertiesReader.class.getClassLoader().getResourceAsStream(PropertiesReader.LOCAL_PROPERTIES_FILE_PATH));
    }

    @VisibleForTesting
    static Properties readLocalProperties(InputStream appPropertiesInputStream) {
        Properties appProperties = new Properties();
        try (InputStream in = appPropertiesInputStream) {
            appProperties.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Cannot read application properties file", e);
        }
        return appProperties;
    }

    @VisibleForTesting
    static Properties readEnvironmentProperties() {
        Properties environmentProperties = new Properties();
        try {
            String env = System.getenv("DATABASE_URL");
            String serverPort = System.getenv("PORT");
            String timeToLive = System.getenv("TIME_TO_LIVE");

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

            environmentProperties.setProperty("server.port", serverPort);
            environmentProperties.setProperty("session.timeToLive", timeToLive);

            return environmentProperties;

        } catch (Exception e) {
            throw new RuntimeException("Cannot read environment properties", e);
        }
    }
}