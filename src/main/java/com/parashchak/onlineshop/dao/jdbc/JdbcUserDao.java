package com.parashchak.onlineshop.dao.jdbc;

import com.parashchak.onlineshop.dao.UserDao;
import com.parashchak.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class JdbcUserDao implements UserDao {

    private static final String FIND_USER = "SELECT id, login, password, salt FROM accounts WHERE login=?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByUserName(String userName) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_USER,
                    new BeanPropertyRowMapper<>(User.class), userName));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}