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
    @ResponseBody
    public String getAll() {
        return showAll();
    }

    @RequestMapping(path = "/products/add", method = RequestMethod.GET)
    @ResponseBody
    public String loadAddPage() {
        return templater.getPage("addPage.html");
    }

    @RequestMapping(path = "/products/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(HttpServletRequest request) throws IOException {
        Product product = toProduct(request);
        productService.add(product);
        return templater.getPage("addPageSuccessful.html");
    }

    @RequestMapping(path = "/products/search", method = RequestMethod.GET)
    @ResponseBody
    public String search(@RequestParam("search-text") String searchText) {
        List<Product> productsSearchList = productService.search(searchText);
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("products", productsSearchList);
        return templater.getPage("viewPage.html", pageData);
    }

    @RequestMapping(path = "/products/edit", method = RequestMethod.GET)
    @ResponseBody
    public String getById(@RequestParam("id") int userId) {
        Product product = productService.getById(userId).orElseThrow();
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("product", product);
        return templater.getPage("editPage.html", pageData);
    }

    @RequestMapping(path = "/products/edit", method = RequestMethod.POST)
    @ResponseBody
    public String edit(HttpServletRequest request) {
        Product product = toProduct(request);
        productService.update(product);
        return templater.getPage("editPageSuccessful.html");
    }

    @RequestMapping(path = "/products/delete", method = RequestMethod.GET)
    public void delete(@RequestParam("id") int userId, HttpServletResponse response) throws IOException {
        productService.delete(userId);
        response.sendRedirect("/products");
    }

    private String showAll() {
        List<Product> allProducts = productService.getAll();
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("products", allProducts);
        return templater.getPage("viewPage.html", pageData);
    }
}