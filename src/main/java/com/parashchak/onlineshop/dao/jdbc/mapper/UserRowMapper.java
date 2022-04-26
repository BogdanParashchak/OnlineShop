package com.parashchak.onlineshop.dao.jdbc.mapper;

import com.parashchak.onlineshop.entity.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class UserRowMapper {

    @SneakyThrows
    public User mapRow(ResultSet resultSet) {
        int id = resultSet.getInt("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        String salt = resultSet.getString("salt");
        return User.builder()
                .id(id)
                .login(login)
                .password(password)
                .salt(salt)
                .build();
    }
}