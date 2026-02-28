package com.example.menunfl.controller;

import com.example.menunfl.dto.ProductRequest;
import com.example.menunfl.entity.product.Product;
import com.example.menunfl.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu/product")
public class ProductController {

    private final ProductService  productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody ProductRequest product) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Product>> getById(@PathVariable Long id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<Product> delete(@RequestBody Product product) {
        return null;
    }
}

