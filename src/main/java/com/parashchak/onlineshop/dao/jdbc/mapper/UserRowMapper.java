package com.parashchak.onlineshop.dao.jdbc.mapper;

import com.parashchak.onlineshop.entity.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.time.LocalDateTime;

public class UserRowMapper {

    @SneakyThrows
    public User mapRow(ResultSet resultSet) {
        int id = resultSet.getInt("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        LocalDateTime creationDate = resultSet.getTimestamp("creation_date").toLocalDateTime();
        return User.builder()
                .id(id)
                .login(login)
                .password(password)
                .creationDate(creationDate)
                .build();
    }
}