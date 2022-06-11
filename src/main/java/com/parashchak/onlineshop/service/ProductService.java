package com.parashchak.onlineshop.service;

import com.parashchak.onlineshop.dao.ProductDao;
import com.parashchak.onlineshop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAll() {
        return productDao.getAll();
    }

    public void add(Product product) {
        product.setCreationDate(LocalDateTime.now());
        productDao.add(product);
    }

    public void delete(int id) {
        productDao.delete(id);
    }

    public void update(Product product) {
        product.setCreationDate(LocalDateTime.now());
        productDao.update(product);
    }

    public Optional<Product> findById(int id) {
        return productDao.findById(id);
    }

    public List<Product> search(String searchText) {
        return productDao.search(searchText);
    }
}