package com.parashchak.onlineshop.security;

import com.parashchak.onlineshop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
class Session {
    private final User user;
    private final String userToken;
    private final LocalDateTime expireDateTime;
}