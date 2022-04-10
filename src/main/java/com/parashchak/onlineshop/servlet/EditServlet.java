package com.parashchak.onlineshop.servlet;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.presentation.PageGenerator;
import com.parashchak.onlineshop.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.parashchak.onlineshop.servlet.RequestProductMapper.mapProduct;

public class EditServlet extends HttpServlet {

    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageData = new HashMap<>();
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getProductById(id);
        pageData.put("product", product);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("editPage.html", pageData);
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = mapProduct(request);
        productService.updateProduct(product);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("editPageSuccesful.html");
        response.getWriter().write(page);
    }
}
