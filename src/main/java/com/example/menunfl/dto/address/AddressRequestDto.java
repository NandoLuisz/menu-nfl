package com.example.menunfl.dto.address;

import com.example.menunfl.entity.address.STATES;

import java.util.UUID;

public record AddressRequestDto(
        String userEmail,
        String number,
        String street,
        String city,
        STATES state,
        String zip,
        String complement
) {
}