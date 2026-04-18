package com.example.menunfl.repository;

import com.example.menunfl.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByNameIgnoreCase(String productDefault);

    Optional<Product> findByNameIgnoreCase(String productDefault);

}
