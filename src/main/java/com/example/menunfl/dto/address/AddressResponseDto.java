package com.example.menunfl.dto.address;

import com.example.menunfl.entity.address.Address;
import com.example.menunfl.entity.address.STATES;
import jakarta.validation.constraints.NotBlank;

public record AddressResponseDto(
        String number,
        String street,
        String city,
        STATES states,
        String zip,
        String neighborhood,
        String complement) {

    public static AddressResponseDto fromEntity(Address data) {
        return new AddressResponseDto(
                data.getNumber(),
                data.getStreet(),
                data.getCity(),
                data.getState(),
                data.getZip(),
                data.getNeighborhood(),
                data.getComplement()
        );
    }
}
