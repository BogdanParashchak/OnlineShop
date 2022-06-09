package com.parashchak.onlineshop.dao;

import com.parashchak.onlineshop.entity.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByLogin(String login);
}