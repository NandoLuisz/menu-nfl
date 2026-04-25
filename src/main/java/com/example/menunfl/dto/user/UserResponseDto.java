package com.example.menunfl.dto.user;

import com.example.menunfl.entity.user.User;

public record UserResponseDto(String username, String email, String phone) {
    public static UserResponseDto fromEntity(User user) {
        return new UserResponseDto(user.getUsername(), user.getEmail(), user.getPhone());
    }
}
