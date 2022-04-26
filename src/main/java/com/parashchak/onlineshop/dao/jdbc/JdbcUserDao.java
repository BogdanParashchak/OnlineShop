package com.parashchak.onlineshop.dao.jdbc;

import com.parashchak.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.parashchak.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RequiredArgsConstructor
public class JdbcUserDao {

    private final static UserRowMapper userRowMapper = new UserRowMapper();
    private static final String GET_USER = "SELECT id,login, password, salt FROM accounts WHERE login=?";

    private final DataSource dataSource;

    public User get(String login) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER)) {
            preparedStatement.setString(1, login);
            User user = null;
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = userRowMapper.mapRow(resultSet);
                }
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Unable to get user from DB", e);
        }
    }
}