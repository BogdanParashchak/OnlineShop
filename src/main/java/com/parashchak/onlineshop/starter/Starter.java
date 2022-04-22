package com.parashchak.onlineshop.starter;

import com.parashchak.onlineshop.dao.jdbc.*;
import com.parashchak.onlineshop.service.ProductService;
import com.parashchak.onlineshop.web.servlet.AddServlet;
import com.parashchak.onlineshop.web.servlet.EditServlet;
import com.parashchak.onlineshop.web.servlet.LoginServlet;
import com.parashchak.onlineshop.web.servlet.ViewAllServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.parashchak.onlineshop.configuration.PortConfigurator.getPort;
import static com.parashchak.onlineshop.configuration.PropertiesReader.getConfigProperties;

public class Starter {
    public static void main(String[] args) throws Exception {

        Properties configProperties = getConfigProperties();

        JdbcConnectionFactory jdbcConnectionFactory = new JdbcConnectionFactory(configProperties);
        JdbcProductDao jdbcProductDao = new JdbcProductDao(jdbcConnectionFactory);

        ProductService productService = new ProductService(jdbcProductDao);

        List<String> sessionList = new ArrayList<>();

        ViewAllServlet viewAllServlet = new ViewAllServlet(productService, sessionList);
        AddServlet addServlet = new AddServlet(productService);
        EditServlet editServlet = new EditServlet(productService);
        LoginServlet loginServlet = new LoginServlet(sessionList);

        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.addServlet(new ServletHolder(viewAllServlet), "/");
        contextHandler.addServlet(new ServletHolder(viewAllServlet), "/products");
        contextHandler.addServlet(new ServletHolder(addServlet), "/products/add");
        contextHandler.addServlet(new ServletHolder(editServlet), "/products/edit");
        contextHandler.addServlet(new ServletHolder(loginServlet), "/login");

        Server server = new Server(getPort(configProperties));
        server.setHandler(contextHandler);
        server.start();
    }
}