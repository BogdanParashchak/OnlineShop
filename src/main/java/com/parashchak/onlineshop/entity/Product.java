package com.parashchak.onlineshop.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class Product {

    private int id;

    private String name;

    private double price;

    private LocalDateTime creationDate;
}