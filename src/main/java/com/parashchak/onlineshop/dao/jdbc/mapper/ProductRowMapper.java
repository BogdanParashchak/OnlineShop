package com.parashchak.onlineshop.dao.jdbc.mapper;

import com.parashchak.onlineshop.entity.Product;
import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalDateTime;

public class ProductRowMapper {

    @SneakyThrows
    public Product mapRow(ResultSet resultSet) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        LocalDateTime creationDate = resultSet.getTimestamp("creation_date").toLocalDateTime();
        String description = resultSet.getString("description");
        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .creationDate(creationDate)
                .description(description)
                .build();
    }
}