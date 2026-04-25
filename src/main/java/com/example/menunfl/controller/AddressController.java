package com.example.menunfl.controller;

import com.example.menunfl.dto.address.AddressRequestDto;
import com.example.menunfl.dto.address.AddressResponseDto;
import com.example.menunfl.entity.address.Address;
import com.example.menunfl.entity.address.STATES;
import com.example.menunfl.entity.user.User;
import com.example.menunfl.service.AddressService;
import com.example.menunfl.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/address")
public class AddressController {
    private final AddressService addressService;
    private final UserService userService;

    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @PostMapping("/user/add-address")
    public ResponseEntity<AddressResponseDto> addAddress(@RequestBody AddressRequestDto data) {
        User user = userService.findByEmail(data.userEmail()).orElseThrow(() -> new RuntimeException("User with email " + data.userEmail() + " not found"));
        Address newAddress = new Address();
        newAddress.setNumber(data.number());
        newAddress.setStreet(data.street());
        newAddress.setCity(data.city());
        newAddress.setState(STATES.valueOf(String.valueOf(data.state())));
        newAddress.setZip(data.zip());
        newAddress.setComplement(data.complement());
        user.addAddress(newAddress);
        newAddress.setUser(user);
        userService.save(user);
        addressService.saveNewAddress(newAddress);
        return ResponseEntity.ok(AddressResponseDto.fromEntity(newAddress));
    }

    @GetMapping("/user/{userId}/addresses")
    public ResponseEntity<List<AddressResponseDto>> getAllAddressesByUser(
            @PathVariable UUID userId) {

        var list = addressService.getAllAddressesById(userId)
                .stream()
                .map(AddressResponseDto::fromEntity)
                .toList();

        return ResponseEntity.ok(list);
    }
}