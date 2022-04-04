package com.example.springcore_homework.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private String username;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
