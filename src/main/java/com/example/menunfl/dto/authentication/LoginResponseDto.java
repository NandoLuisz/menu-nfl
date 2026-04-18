package com.example.menunfl.dto.authentication;

import java.util.UUID;

public record LoginResponseDto(
        String token,
        UUID id,
        String name){
}
