package com.example.menunfl.dto.customer;

import com.example.menunfl.entity.customer.Customer;
import com.example.menunfl.entity.enums.CUSTOMER_ROLE;

import java.time.LocalDate;

public record CustomerResponseDto(
        String name,
        String email,
        String phone,
        LocalDate birthday,
        CUSTOMER_ROLE role
) {

    public CustomerResponseDto(Customer customer) {
        this(customer.getName(), customer.getEmail(), customer.getPhone(), customer.getBirthday(), customer.getCustomerRole());
    }

    public static CustomerResponseDto fromEntity(Customer data) {
        return new CustomerResponseDto(
                data.getName(),
                data.getEmail(),
                data.getPhone(),
                data.getBirthday(),
                data.getCustomerRole()
        );
    }
}
