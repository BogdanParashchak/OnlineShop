package com.parashchak.onlineshop;

import com.parashchak.onlineshop.dao.ProductDao;
import com.parashchak.onlineshop.dao.UserDao;
import com.parashchak.onlineshop.dao.jdbc.JdbcConnectionFactory;
import com.parashchak.onlineshop.dao.jdbc.JdbcProductDao;
import com.parashchak.onlineshop.dao.jdbc.JdbcUserDao;
import com.parashchak.onlineshop.security.SecurityService;
import com.parashchak.onlineshop.service.*;
import com.parashchak.onlineshop.web.security.SecurityFilter;
import com.parashchak.onlineshop.web.servlet.*;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

import javax.sql.DataSource;
import java.util.*;

import static com.parashchak.onlineshop.configuration.PortConfigurator.getPort;
import static com.parashchak.onlineshop.configuration.PropertiesReader.getAppProperties;

public class Starter {
    public static void main(String[] args) throws Exception {

        // read properties
        Properties configProperties = getAppProperties();

        //configure DB connection and DAOs
        DataSource dataSource = new JdbcConnectionFactory(configProperties);
        ProductDao productDao = new JdbcProductDao(dataSource);
        UserDao userDao = new JdbcUserDao(dataSource);

        //services
        ProductService productService = new ProductService(productDao);
        UserService userService = new UserService(userDao);
        SecurityService securityService = new SecurityService(userService);

        //filters
        SecurityFilter securityFilter = new SecurityFilter(securityService);

        //servlets
        ViewAllServlet viewAllServlet = new ViewAllServlet(productService);
        SearchServlet searchServlet = new SearchServlet(productService);
        LoginServlet loginServlet = new LoginServlet(userService, securityService);
        AddServlet addServlet = new AddServlet(productService);
        EditServlet editServlet = new EditServlet(productService);
        DeleteServlet deleteServlet = new DeleteServlet(productService);

        //configure web server
        ServletContextHandler contextHandler = new ServletContextHandler();

        contextHandler.addServlet(new ServletHolder(viewAllServlet), "");
        contextHandler.addServlet(new ServletHolder(viewAllServlet), "/products");
        contextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        contextHandler.addServlet(new ServletHolder(searchServlet), "/products/search");
        contextHandler.addServlet(new ServletHolder(addServlet), "/products/add");
        contextHandler.addServlet(new ServletHolder(editServlet), "/products/edit");
        contextHandler.addServlet(new ServletHolder(deleteServlet), "/products/delete");

        contextHandler.addFilter(new FilterHolder(securityFilter), "", EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter(new FilterHolder(securityFilter), "/products", EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter(new FilterHolder(securityFilter), "/products/search", EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter(new FilterHolder(securityFilter), "/products/add", EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter(new FilterHolder(securityFilter), "/products/edit", EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter(new FilterHolder(securityFilter), "/products/delete", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(getPort(configProperties));
        server.setHandler(contextHandler);
        server.start();
    }
}