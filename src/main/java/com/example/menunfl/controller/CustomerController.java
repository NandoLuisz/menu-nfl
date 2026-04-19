package com.example.menunfl.controller;

import com.example.menunfl.dto.address.AddressResponseDto;
import com.example.menunfl.dto.customer.CustomerRequestDto;
import com.example.menunfl.dto.customer.CustomerResponseDto;
import com.example.menunfl.entity.customer.Customer;
import com.example.menunfl.repository.CustomerRepository;
import com.example.menunfl.service.AddressService;
import com.example.menunfl.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final AddressService addressService;

    public CustomerController(CustomerService customerService, AddressService addressService) {
        this.customerService = customerService;
        this.addressService = addressService;
    }

    @GetMapping("get-all-customers")
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        return ResponseEntity.ok(
                customerService
                        .findAllCustomers()
                        .stream()
                        .map(CustomerResponseDto::fromEntity)
                        .toList());
    }

    @GetMapping("get-customer-by-id/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable UUID id) {
        var customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(CustomerResponseDto.fromEntity(customer));
    }

    @PutMapping("update-customer/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable UUID id, @RequestBody CustomerRequestDto data) {
        var customer = customerService.getCustomerById(id);
        customer.setName(data.name());
        customer.setPhone(data.phone());
        customerService.saveCustomer(customer);
        return ResponseEntity.ok(CustomerResponseDto.fromEntity(customer));
    }

    @GetMapping("/customers/{customerId}/addresses")
    public ResponseEntity<List<AddressResponseDto>> getAllAddressesByCustomer(
            @PathVariable UUID customerId) {

        var list = addressService.getAllAddressesById(customerId)
                .stream()
                .map(AddressResponseDto::fromEntity)
                .toList();

        return ResponseEntity.ok(list);
    }
}
