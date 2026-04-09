package com.example.menunfl.controller;

import com.example.menunfl.dto.address.AddressRequestDto;
import com.example.menunfl.dto.address.AddressResponseDto;
import com.example.menunfl.entity.address.Address;
import com.example.menunfl.entity.customer.Customer;
import com.example.menunfl.service.AddressService;
import com.example.menunfl.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/address")
public class AddressController {
    private final AddressService addressService;
    private final CustomerService customerService;

    public AddressController(AddressService addressService, CustomerService customerService) {
        this.addressService = addressService;
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<AddressResponseDto> addAddress(@RequestBody AddressRequestDto data) {
        Customer customer = customerService.getCustomerById(data.idCustomer());
        Address newAddress = new Address();
        newAddress.setNumber(data.number());
        newAddress.setStreet(data.street());
        newAddress.setCity(data.city());
        newAddress.setState(data.states());
        newAddress.setZip(data.zip());
        newAddress.setNeighborhood(data.neighborhood());
        newAddress.setComplement(data.complement());
        customer.getAddresses().add(newAddress);
        addressService.saveNewAddress(newAddress);
        return ResponseEntity.ok(AddressResponseDto.fromEntity(newAddress));
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses(@RequestBody UUID idCustomer) {
        var list = addressService.findAllAddresses().stream().map(AddressResponseDto::fromEntity).toList();
        return ResponseEntity.ok(list);
    }
}
