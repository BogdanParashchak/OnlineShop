package com.parashchak.onlineshop.starter;

import com.parashchak.onlineshop.dao.jdbc.ProductDao;
import com.parashchak.onlineshop.service.ProductService;
import com.parashchak.onlineshop.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

public class Starter {
    public static void main(String[] args) throws Exception {

        ProductDao productDao = new ProductDao();

        ProductService productService = new ProductService(productDao);

        ViewAllServlet viewAllServlet = new ViewAllServlet(productService);
        AddServlet addServlet = new AddServlet(productService);
        EditServlet editServlet = new EditServlet(productService);

        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.addServlet(new ServletHolder(viewAllServlet), "/");
        contextHandler.addServlet(new ServletHolder(viewAllServlet), "/products");
        contextHandler.addServlet(new ServletHolder(addServlet), "/products/add");
        contextHandler.addServlet(new ServletHolder(editServlet), "/products/edit");

        Server server = new Server(Integer.parseInt(System.getProperty("server.port")));
        server.setHandler(contextHandler);
        server.start();
    }
}