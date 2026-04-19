package com.example.menunfl.dto.customer;

import com.example.menunfl.entity.customer.Customer;
import com.example.menunfl.entity.enums.CUSTOMER_ROLE;

import java.time.LocalDate;
import java.util.UUID;

public record CustomerResponseDto(
        UUID id,
        String name,
        String email,
        String phone,
        LocalDate birthday,
        CUSTOMER_ROLE role
) {

    public CustomerResponseDto(Customer customer) {
        this(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone(), customer.getBirthday(), customer.getCustomerRole());
    }

    public static CustomerResponseDto fromEntity(Customer data) {
        return new CustomerResponseDto(
                data.getId(),
                data.getName(),
                data.getEmail(),
                data.getPhone(),
                data.getBirthday(),
                data.getCustomerRole()
        );
    }
}
