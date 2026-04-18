package com.example.menunfl.controller;

import com.example.menunfl.dto.authentication.CustomerResponseRegisterDto;
import com.example.menunfl.dto.authentication.LoginDto;
import com.example.menunfl.dto.authentication.LoginResponseDto;
import com.example.menunfl.dto.authentication.RegisterRequestDto;
import com.example.menunfl.entity.customer.Customer;
import com.example.menunfl.entity.enums.CUSTOMER_ROLE;
import com.example.menunfl.infra.security.TokenService;
import com.example.menunfl.repository.CustomerRepository;
import com.example.menunfl.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RequestMapping("api/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final TokenService tokenService;
    private final CustomerService customerService;

    public AuthenticationController(AuthenticationManager authenticationManager, CustomerRepository customerRepository, TokenService tokenService, CustomerService customerService) {
        this.authenticationManager = authenticationManager;
        this.customerRepository = customerRepository;
        this.tokenService = tokenService;
        this.customerService = customerService;
    }


    @PostMapping("/login-customer")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginDto data){
        var customer = customerRepository.findByName(data.name());
        if(customer == null) {
            throw new RuntimeException("Customer not found");
        }

        Customer customerExist = customerRepository.getCustomerByName(customer.getUsername());
        UUID id = customerExist.getId();

        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.name(), data.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((Customer) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDto(token, id, data.name()));
        } catch (Exception error) {
            throw new RuntimeException("Password Incorrect Format");
        }
    }

    @PostMapping("/register-customer")
    public ResponseEntity<CustomerResponseRegisterDto> registerUser(@RequestBody RegisterRequestDto data) throws IOException {
        if (customerService.findCustomerByName(data.name())) {
            throw new RuntimeException("Customer already registered.");
        }
        if (customerService.findCustomerByEmail(data.name())) {
            throw new RuntimeException("Email already registered.");
        }

        Customer response = customerService.registerCustomer(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerResponseRegisterDto.from(response));
    }
}
