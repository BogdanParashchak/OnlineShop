package com.parashchak.onlineshop.starter;

import com.parashchak.onlineshop.dao.jdbc.JdbcConnectionFactory;
import com.parashchak.onlineshop.dao.jdbc.JdbcProductDao;
import com.parashchak.onlineshop.dao.jdbc.JdbcUserDao;
import com.parashchak.onlineshop.service.*;
import com.parashchak.onlineshop.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

import java.util.*;

import static com.parashchak.onlineshop.configuration.PortConfigurator.getPort;
import static com.parashchak.onlineshop.configuration.PropertiesReader.getAppProperties;

public class Starter {
    public static void main(String[] args) throws Exception {

        Properties configProperties = getAppProperties();

        JdbcConnectionFactory jdbcConnectionFactory = new JdbcConnectionFactory(configProperties);
        JdbcProductDao jdbcProductDao = new JdbcProductDao(jdbcConnectionFactory);
        JdbcUserDao jdbcUserDao = new JdbcUserDao(jdbcConnectionFactory);

        ProductService productService = new ProductService(jdbcProductDao);
        UserService userService = new UserService(jdbcUserDao);
        SecurityService securityService = new SecurityService();

        ViewAllServlet viewAllServlet = new ViewAllServlet(productService);
        SearchServlet searchServlet = new SearchServlet(productService);

        LoginServlet loginServlet = new LoginServlet(userService, securityService);
        AddServlet addServlet = new AddServlet(productService, securityService);
        EditServlet editServlet = new EditServlet(productService, securityService);
        DeleteServlet deleteServlet = new DeleteServlet(productService, securityService);

        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.addServlet(new ServletHolder(viewAllServlet), "");
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