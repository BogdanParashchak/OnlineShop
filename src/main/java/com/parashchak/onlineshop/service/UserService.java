package com.parashchak.onlineshop.service;

import com.parashchak.onlineshop.dao.jdbc.JdbcUserDao;
import com.parashchak.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

    private final JdbcUserDao jdbcUserDao;

    public User get(String login, String password) {
        return jdbcUserDao.get(login, password);
    }

    public boolean validate(String login, String password){
        return jdbcUserDao.validate(login, password);
    }
}