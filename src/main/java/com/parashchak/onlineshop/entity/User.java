package com.parashchak.onlineshop.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String userName;
    private String password;
    private String salt;
}