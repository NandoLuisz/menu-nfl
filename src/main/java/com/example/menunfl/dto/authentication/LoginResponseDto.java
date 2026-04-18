package com.example.menunfl.entity.authentication;

import java.util.UUID;

public record LoginResponseDto(
        String token,
        UUID id,
        String name){
}
