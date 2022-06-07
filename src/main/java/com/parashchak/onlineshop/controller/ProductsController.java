package com.parashchak.onlineshop.controller;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.service.*;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class ProductsController {

    private final PageGenerator pageGenerator;

    @Autowired
    private ProductService productService;

    @Autowired
    public ProductsController(PageGenerator pageGenerator) {
        this.pageGenerator = pageGenerator;
    }

    @GetMapping(path = {"/products", "/"})
    @ResponseBody
    public String getAll() {
        return showAll();
    }

    @GetMapping(path = "/products/add")
    @ResponseBody
    public String loadAddPage() {
        return pageGenerator.getPage("addPage.html");
    }

    @PostMapping(path = "/products/add")
    public String add(@RequestParam String name,
                      @RequestParam double price,
                      @RequestParam String description) throws IOException {
        Product product = Product.builder()
                .name(name)
                .price(price)
                .creationDate(LocalDateTime.now())
                .description(description)
                .build();
        productService.add(product);
        return "redirect:/products";
    }

    @GetMapping(path = "/products/search")
    @ResponseBody
    public String search(@RequestParam("search-text") String searchText) {
        List<Product> productsSearchList = productService.search(searchText);
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("products", productsSearchList);
        return pageGenerator.getPage("viewPage.html", pageData);
    }

    @GetMapping(path = "/products/edit")
    @ResponseBody
    public String getById(@RequestParam("id") int userId) {
        Product product = productService.getById(userId).orElseThrow();
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("product", product);
        return pageGenerator.getPage("editPage.html", pageData);
    }

    @PostMapping(path = "/products/edit")
    public String edit(@RequestParam String name,
                       @RequestParam double price,
                       @RequestParam String description,
                       @RequestParam int id) {
        Product product = Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .creationDate(LocalDateTime.now())
                .description(description)
                .build();
        productService.update(product);
        return "redirect:/products";
    }

    @PostMapping(path = "/products/delete")
    public String delete(@RequestParam("id") int userId) {
        productService.delete(userId);
        return "redirect:/products";
    }

    private String showAll() {
        List<Product> allProducts = productService.getAll();
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("products", allProducts);
        return pageGenerator.getPage("viewPage.html", pageData);
    }
}