package com.example.menunfl.infra.initializer;

import com.example.menunfl.entity.customer.Customer;
import com.example.menunfl.entity.enums.CUSTOMER_ROLE;
import com.example.menunfl.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomerInitializer implements CommandLineRunner {
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public void run(String... args) {

        if (!customerRepository.existsByName("admin")) {

            Customer customer = new Customer();
            customer.setName("admin");
            customer.setEmail("admin@gmail.com");
            customer.setPassword("12345");
            customer.setPhone("1234567890");
            customer.setCustomerRole(CUSTOMER_ROLE.ADMIN);
            customer.setActive(true);

            customerRepository.save(customer);

            System.out.println("Client administrator created!");
        }
    }
}
