package com.parashchak.onlineshop.entity;

import java.time.LocalDateTime;

public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDateTime creationDateAndTime;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", creationDateAndTime=" + creationDateAndTime +
                '}';
    }
}