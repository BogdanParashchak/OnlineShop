package com.parashchak.onlineshop.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
class Session {
    private final String token;
    private final LocalDateTime expireDateTime;
}