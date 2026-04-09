package com.example.menunfl.controller;

import com.example.menunfl.entity.authentication.LoginDto;
import com.example.menunfl.entity.authentication.LoginResponseDto;
import com.example.menunfl.entity.authentication.RegisterRequestDto;
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

    private AuthenticationManager authenticationManager;
    private CustomerRepository customerRepository;
    private TokenService tokenService;
    private CustomerService customerService;


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
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto data) throws IOException {
        if (customerRepository.findByName(data.name()) != null) {
            return ResponseEntity.badRequest().body("Customer already registered.");
        }

        if (customerRepository.existsByEmail(data.email())) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(data.password());

        Customer newCustomer = new Customer(
                data.name(),
                data.email(),
                encryptedPassword,
                CUSTOMER_ROLE.CUSTOMER);

        customerRepository.save(newCustomer);

        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }
}
