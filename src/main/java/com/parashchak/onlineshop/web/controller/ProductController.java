package com.parashchak.onlineshop.web.controller;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/products", "/"})
    public String getAll(Model model) {
        List<Product> allProducts = productService.getAll();
        model.addAttribute("products", allProducts);
        return "viewPage";
    }

    @GetMapping("/products/add")
    public String loadAddPage() {
        return "addPage";
    }

    @PostMapping("/products/add")
    public String add(@RequestParam String name,
                      @RequestParam double price,
                      @RequestParam String description) throws IOException {
        Product product = Product.builder()
                .name(name)
                .price(price)
                .description(description)
                .build();
        productService.add(product);
        return "redirect:/products";
    }

    @GetMapping("/products/search")
    public String search(@RequestParam String searchText, Model model) {
        List<Product> productsSearchList = productService.search(searchText);
        model.addAttribute("products", productsSearchList);
        return "viewPage";
    }

    @GetMapping("/products/edit")
    public String loadEditPage(@RequestParam int id, Model model) {
        Product product = productService.findById(id).orElseThrow();
        model.addAttribute("product", product);
        return "editPage";
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
                .description(description)
                .build();
        productService.update(product);
        return "redirect:/products";
    }

    @PostMapping("/products/delete")
    public String delete(@RequestParam int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}