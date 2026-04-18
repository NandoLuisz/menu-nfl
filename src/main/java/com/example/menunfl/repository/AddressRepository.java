package com.example.menunfl.repository;

import com.example.menunfl.entity.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCustomerId(UUID customerId);
}
