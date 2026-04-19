package com.example.menunfl.repository;

import com.example.menunfl.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    UserDetails findByName(String name);

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    Customer getCustomerByName(String username);

    Customer findByEmail(String email);
}
