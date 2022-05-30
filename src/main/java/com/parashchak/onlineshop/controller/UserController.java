package com.parashchak.onlineshop.controller;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.service.*;
import com.parashchak.templater.Templater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

import static com.parashchak.onlineshop.web.mapper.ProductRequestMapper.toProduct;

@Controller
public class UserController {

    @Autowired
    private Templater templater;

    @Autowired
    private ProductService productService;

    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public void getAll(HttpServletResponse response) throws IOException {
        showAll(response);
    }

    @RequestMapping(path = "/products/add", method = RequestMethod.GET)
    public void loadAddPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String page = templater.getPage("addPage.html");
        response.getWriter().write(page);
    }

    @RequestMapping(path = "/products/add", method = RequestMethod.POST)
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = toProduct(request);
        productService.add(product);
        String page = templater.getPage("addPageSuccessful.html");
        response.getWriter().write(page);
    }

    @RequestMapping(path = "/products/search", method = RequestMethod.GET)
    public void search(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageData = new HashMap<>();
        String searchText = request.getParameter("search-text");
        List<Product> productsSearchList = productService.search(searchText);
        pageData.put("products", productsSearchList);
        String page = templater.getPage("viewPage.html", pageData);
        response.getWriter().write(page);
    }

    @RequestMapping(path = "/products/edit", method = RequestMethod.GET)
    public void getById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageData = new HashMap<>();
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getById(id).orElseThrow();
        pageData.put("product", product);
        String page = templater.getPage("editPage.html", pageData);
        response.getWriter().write(page);
    }

    @RequestMapping(path = "/products/edit", method = RequestMethod.POST)
    public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = toProduct(request);
        productService.update(product);
        String page = templater.getPage("editPageSuccessful.html");
        response.getWriter().write(page);
    }

    @RequestMapping(path = "/products/delete", method = RequestMethod.GET)
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.delete(id);
        response.sendRedirect("/products");
    }

    private void showAll(HttpServletResponse response) throws IOException {
        Map<String, Object> pageData = new HashMap<>();
        List<Product> allProducts = productService.getAll();
        pageData.put("products", allProducts);
        String page = templater.getPage("viewPage.html", pageData);
        response.getWriter().write(page);
    }
}