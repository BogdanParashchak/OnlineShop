package com.parashchak.onlineshop.starter;

import com.parashchak.onlineshop.dao.jdbc.*;
import com.parashchak.onlineshop.service.ProductService;
import com.parashchak.onlineshop.service.UserService;
import com.parashchak.onlineshop.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

import java.util.*;

import static com.parashchak.onlineshop.configuration.PortConfigurator.getPort;
import static com.parashchak.onlineshop.configuration.PropertiesReader.getConfigProperties;

public class Starter {
    public static void main(String[] args) throws Exception {

        Properties configProperties = getConfigProperties();

        JdbcConnectionFactory jdbcConnectionFactory = new JdbcConnectionFactory(configProperties);
        JdbcProductDao jdbcProductDao = new JdbcProductDao(jdbcConnectionFactory);
        JdbcUserDao jdbcUserDao = new JdbcUserDao(jdbcConnectionFactory);

        ProductService productService = new ProductService(jdbcProductDao);
        UserService userService = new UserService(jdbcUserDao);

        List<String> sessionList = new ArrayList<>();

        ViewAllServlet viewAllServlet = new ViewAllServlet(productService);
        LoginServlet loginServlet = new LoginServlet(userService, sessionList);
        SearchServlet searchServlet = new SearchServlet(productService);

        AddServlet addServlet = new AddServlet(productService, sessionList);
        EditServlet editServlet = new EditServlet(productService, sessionList);
        DeleteServlet deleteServlet = new DeleteServlet(productService, sessionList);

        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.addServlet(new ServletHolder(viewAllServlet), "/");
        contextHandler.addServlet(new ServletHolder(viewAllServlet), "/products");
        contextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        contextHandler.addServlet(new ServletHolder(searchServlet), "/products/search");
        contextHandler.addServlet(new ServletHolder(addServlet), "/products/add");
        contextHandler.addServlet(new ServletHolder(editServlet), "/products/edit");
        contextHandler.addServlet(new ServletHolder(deleteServlet), "/products/delete");

        Server server = new Server(getPort(configProperties));
        server.setHandler(contextHandler);
        server.start();
    }
}