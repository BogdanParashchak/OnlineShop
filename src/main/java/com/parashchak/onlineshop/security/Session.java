package com.parashchak.onlineshop.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
class Session {
    private final String userToken;
    private final LocalDateTime expireDateTime;
}