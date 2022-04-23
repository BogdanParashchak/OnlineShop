package com.parashchak.onlineshop.service;

import com.parashchak.onlineshop.dao.jdbc.JdbcProductDao;
import com.parashchak.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductService {

    private final JdbcProductDao jdbcProductDao;

    public List<Product> getAll() {
        return jdbcProductDao.getAll();
    }

    public void add(Product product) {
        jdbcProductDao.add(product);
    }

    public void delete(int id) {
        jdbcProductDao.delete(id);
    }

    public void update(Product product) {
        jdbcProductDao.update(product);
    }

    public Product getById(int id) {
        return jdbcProductDao.getById(id);
    }

    public List<Product> search(String searchText) {
        return jdbcProductDao.search(searchText);
    }


}