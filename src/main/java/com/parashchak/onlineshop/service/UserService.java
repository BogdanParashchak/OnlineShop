package com.parashchak.onlineshop.service;

import com.parashchak.onlineshop.dao.jdbc.JdbcUserDao;
import com.parashchak.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final JdbcUserDao jdbcUserDao;

    public Optional<User> get(String login) {
        return jdbcUserDao.get(login);
    }
}