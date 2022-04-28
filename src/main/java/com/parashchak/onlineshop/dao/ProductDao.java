package com.parashchak.onlineshop.dao;

import com.parashchak.onlineshop.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> getAll();

    void add(Product product);

    void delete(int id);

    void update(Product product);

    Optional<Product> getById(int id);

    List<Product> search(String searchText);
}
