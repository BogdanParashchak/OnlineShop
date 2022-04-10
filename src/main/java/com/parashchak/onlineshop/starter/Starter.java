package com.parashchak.onlineshop.starter;

import com.parashchak.onlineshop.dao.ProductDao;
import com.parashchak.onlineshop.service.ProductService;
import com.parashchak.onlineshop.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

public class Starter {
    public static void main(String[] args) throws Exception {

        ProductDao productDao = new ProductDao();

        ProductService productService = new ProductService();
        productService.setProductDao(productDao);

        ServletManager servletManager = new ServletManager();
        ServletContextHandler contextHandler = servletManager.manage(productService);

        Server server = new Server(3000);
        server.setHandler(contextHandler);
        server.start();
    }
}