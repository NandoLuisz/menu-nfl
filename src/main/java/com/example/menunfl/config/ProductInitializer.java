package com.example.menunfl.config;

import com.example.menunfl.entity.enums.CATEGORY;
import com.example.menunfl.entity.product.Product;
import com.example.menunfl.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void run(String... args) {

        if (!productRepository.existsByNameIgnoreCase("product default")) {

            Product product = new Product();
            product.setName("product default");
            product.setDescription("product default description");
            product.setPrice(new BigDecimal("0.01"));
            product.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmiqR_gB1aE6SmGpJvgdi6j6MZYtLpcSittA&s");
            product.setActive(true);
            product.setStock(1);
            product.setCategory(CATEGORY.DEFAULT);

            productRepository.save(product);

            System.out.println("Standard product created!");
        }
    }
}
