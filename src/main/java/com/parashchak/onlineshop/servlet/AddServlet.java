package com.parashchak.onlineshop.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.presentation.PageGenerator;
import com.parashchak.onlineshop.service.ProductService;
import jakarta.servlet.http.*;

import java.io.IOException;

import static com.parashchak.onlineshop.servlet.RequestProductMapper.mapProduct;


public class AddServlet extends HttpServlet {

    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addPage.html");
        response.getWriter().write(page);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = mapProduct(request);
        productService.addProduct(product);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addPageSuccesful.html");
        response.getWriter().write(page);
    }
}