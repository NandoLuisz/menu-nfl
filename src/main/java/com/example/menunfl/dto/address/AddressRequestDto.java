package com.example.menunfl.dto.address;

import com.example.menunfl.entity.address.STATES;

import java.util.UUID;

public record AddressRequestDto(
        UUID idCustomer,
        String number,
        String street,
        String city,
        STATES states,
        String zip,
        String neighborhood,
        String complement
) {
}