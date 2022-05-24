package com.parashchak.onlineshop.service;

import com.parashchak.onlineshop.dao.*;
import com.parashchak.onlineshop.dao.jdbc.*;
import com.parashchak.onlineshop.security.SecurityService;

import javax.sql.DataSource;
import java.util.*;

import static com.parashchak.onlineshop.configuration.PropertiesReader.getAppProperties;

public class ServiceLocator {
    public static final Map<Class<?>, Object> SERVICES = new HashMap<>();

    static {
        Properties configProperties = getAppProperties();

        DataSource dataSource = new JdbcConnectionFactory(configProperties);
        ProductDao productDao = new JdbcProductDao(dataSource);
        UserDao userDao = new JdbcUserDao(dataSource);

        ProductService productService = new ProductService(productDao);
        UserService userService = new UserService(userDao);
        SecurityService securityService = new SecurityService(userService);

        addService(ProductService.class, productService);
        addService(SecurityService.class, securityService);
    }

    public static <T> T getService(Class<T> serviceType) {
        return serviceType.cast(SERVICES.get(serviceType));
    }

    public static void addService(Class<?> serviceName, Object service) {
        SERVICES.put(serviceName, service);
    }
}
