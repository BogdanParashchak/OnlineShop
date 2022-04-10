package com.parashchak.onlineshop.servlet;

import com.parashchak.onlineshop.service.ProductService;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ServletManager {
    public ServletContextHandler manage(ProductService productService) {

        ViewAllServlet viewAllServlet = new ViewAllServlet();
        viewAllServlet.setProductService(productService);

        AddServlet addServlet = new AddServlet();
        addServlet.setProductService(productService);

        EditServlet editServlet = new EditServlet();
        editServlet.setProductService(productService);

        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.addServlet(new ServletHolder(viewAllServlet), "/");
        contextHandler.addServlet(new ServletHolder(viewAllServlet), "/products");
        contextHandler.addServlet(new ServletHolder(addServlet), "/products/add");
        contextHandler.addServlet(new ServletHolder(editServlet), "/products/edit");
        return contextHandler;
    }
}