package com.parashchak.onlineshop.entity;

import lombok.*;

public class Product {
    @Getter @Setter
    private int id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private double price;
}