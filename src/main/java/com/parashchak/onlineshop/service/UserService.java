package com.parashchak.onlineshop.service;

import com.parashchak.onlineshop.dao.UserDao;
import com.parashchak.onlineshop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> get(String login) {
        return userDao.findByLogin(login);
    }
}