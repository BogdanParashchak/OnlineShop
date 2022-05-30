package com.parashchak.onlineshop.service;

import com.parashchak.onlineshop.dao.UserDao;
import com.parashchak.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public Optional<User> get(String login) {
        return userDao.get(login);
    }
}