package com.parashchak.onlineshop.service;

import com.parashchak.onlineshop.dao.ProductDao;
import com.parashchak.onlineshop.entity.Product;

import java.util.List;

public class ProductService {

    private ProductDao productDao;

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAll() {
        return productDao.getAllProducts();
    }

    public int addProduct(Product product) {
        return productDao.addProduct(product);
    }

    public int deleteProduct(int id) {
        return productDao.deleteProduct(id);
    }

    public int updateProduct(Product product){
        return productDao.updateProduct(product);
    }

    public Product getProductById(int id) {
        return productDao.getProductById(id);
    }
}