package com.example.menunfl.service;

import com.example.menunfl.dto.authentication.CustomerResponseRegisterDto;
import com.example.menunfl.dto.authentication.RegisterRequestDto;
import com.example.menunfl.entity.customer.Customer;
import com.example.menunfl.entity.enums.CUSTOMER_ROLE;
import com.example.menunfl.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer getCustomerById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer with id " + id + " not found"));
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public boolean findCustomerByName(String name) {
        return customerRepository.existsByName(name);    }

    public boolean findCustomerByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    public Customer registerCustomer(RegisterRequestDto data) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(data.password());
        Customer newCustomer = new Customer(
                data.name(),
                data.email(),
                data.phone(),
                encryptedPassword,
                CUSTOMER_ROLE.CUSTOMER);
        return customerRepository.save(newCustomer);
    }
}
