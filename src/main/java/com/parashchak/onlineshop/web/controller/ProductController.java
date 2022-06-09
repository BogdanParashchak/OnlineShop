package com.parashchak.onlineshop.web.controller;

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
public class ProductController {

    private final PageGenerator pageGenerator;
    private final ProductService productService;

    @Autowired
    public ProductController(PageGenerator pageGenerator, ProductService productService) {
        this.pageGenerator = pageGenerator;
        this.productService = productService;
    }

    @GetMapping({"/products", "/"})
    @ResponseBody
    public String getAll() {
        List<Product> allProducts = productService.getAll();
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("products", allProducts);
        return pageGenerator.getPage("viewPage.html", pageData);
    }

    @GetMapping("/products/add")
    @ResponseBody
    public String loadAddPage() {
        return pageGenerator.getPage("addPage.html");
    }

    @PostMapping("/products/add")
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

    @GetMapping("/products/search")
    @ResponseBody
    public String search(@RequestParam("search-text") String searchText) {
        List<Product> productsSearchList = productService.search(searchText);
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("products", productsSearchList);
        return pageGenerator.getPage("viewPage.html", pageData);
    }

    @GetMapping("/products/edit")
    @ResponseBody
    public String getById(@RequestParam("id") int userId) {
        Product product = productService.getById(userId).orElseThrow();
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("product", product);
        return pageGenerator.getPage("editPage.html", pageData);
    }

    @PostMapping("/products/edit")
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

    @PostMapping("/products/delete")
    public String delete(@RequestParam("id") int userId) {
        productService.delete(userId);
        return "redirect:/products";
    }
}