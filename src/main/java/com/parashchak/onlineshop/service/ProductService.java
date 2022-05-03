package com.parashchak.onlineshop.service;

import com.parashchak.onlineshop.dao.ProductDao;
import com.parashchak.onlineshop.dao.jdbc.JdbcProductDao;
import com.parashchak.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public List<Product> getAll() {
        return productDao.getAll();
    }

    public void add(Product product) {
        productDao.add(product);
    }

    public void delete(int id) {
        productDao.delete(id);
    }

    public void update(Product product) {
        productDao.update(product);
    }

    public Optional<Product> getById(int id) {
        return productDao.getById(id);
    }

    public List<Product> search(String searchText) {
        return productDao.search(searchText);
    }
}