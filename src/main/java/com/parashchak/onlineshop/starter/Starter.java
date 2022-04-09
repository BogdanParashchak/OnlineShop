package com.parashchak.onlineshop.starter;

import com.parashchak.onlineshop.dao.ProductDao;
import com.parashchak.onlineshop.service.ProductService;
import com.parashchak.onlineshop.servlet.AddServlet;
import com.parashchak.onlineshop.servlet.ViewAllServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {
    public static void main(String[] args) throws Exception {

        ProductDao productDao = new ProductDao();

        ProductService productService = new ProductService();
        productService.setProductDao(productDao);

        ViewAllServlet viewAllServlet = new ViewAllServlet();
        viewAllServlet.setProductService(productService);

        AddServlet addServlet = new AddServlet();
        addServlet.setProductService(productService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(viewAllServlet), "/products");
        contextHandler.addServlet(new ServletHolder(viewAllServlet), "/");
        contextHandler.addServlet(new ServletHolder(addServlet), "/products/add");

        Server server = new Server(3000);
        server.setHandler(contextHandler);
        server.start();
    }
}