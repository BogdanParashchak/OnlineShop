package com.parashchak.onlineshop.dao.jdbc;

import com.parashchak.onlineshop.dao.UserDao;
import com.parashchak.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.parashchak.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JdbcUserDao implements UserDao {

    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final String GET_USER = "SELECT id,login, password, salt FROM accounts WHERE login=?";

    private final DataSource dataSource;

    public Optional<User> get(String login) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                log.info("Executed: {}", preparedStatement);
                if (resultSet.next()) {
                    return Optional.of(USER_ROW_MAPPER.mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to get user from DB", e);
        }
    }
}