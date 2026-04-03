package com.example.menunfl.controller;

import com.example.menunfl.dto.ProductRequestDto;
import com.example.menunfl.dto.ProductResponseDto;
import com.example.menunfl.entity.product.Product;
import com.example.menunfl.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private final ProductService  productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> save(@RequestBody @Valid ProductRequestDto product) {
        ProductResponseDto response = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAll() {
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Product product) {
        productService.updateProduct(product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product successfully updated.");
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody Product product) {
        productService.deleteProduct(product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product successfully deleted.");
    }
}

