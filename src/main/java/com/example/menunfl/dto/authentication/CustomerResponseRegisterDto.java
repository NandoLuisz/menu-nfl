package com.example.menunfl.dto.authentication;

import com.example.menunfl.entity.customer.Customer;

public record CustomerResponseRegisterDto(String name, String email, String phone) {
    public static CustomerResponseRegisterDto from(Customer customer) {
        return new CustomerResponseRegisterDto(customer.getName(), customer.getEmail(), customer.getPhone());
    }
}
