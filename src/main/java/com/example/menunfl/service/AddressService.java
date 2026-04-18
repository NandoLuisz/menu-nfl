package com.example.menunfl.service;

import com.example.menunfl.entity.address.Address;
import com.example.menunfl.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Optional<Address> getAddressById(Long addressId) {
        return addressRepository.findById(addressId);
    }
    public void saveNewAddress(Address address) {
        addressRepository.save(address);
    }

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }
}
