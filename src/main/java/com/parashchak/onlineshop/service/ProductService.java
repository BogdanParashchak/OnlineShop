package com.parashchak.onlineshop.service;

import com.parashchak.onlineshop.dao.jdbc.ProductDao;
import com.parashchak.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

    public void update(Product product){
        productDao.update(product);
    }

    public Product getById(int id) {
        return productDao.getById(id);
    }
}