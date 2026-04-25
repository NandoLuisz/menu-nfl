package com.example.menunfl.controller;

import com.example.menunfl.dto.product.ProductRequestDto;
import com.example.menunfl.dto.product.ProductResponseDto;
import com.example.menunfl.entity.product.Product;
import com.example.menunfl.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private final ProductService  productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("create")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ProductResponseDto> create(@RequestBody ProductRequestDto product) {
        ProductResponseDto response = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("find-all")
    public ResponseEntity<List<ProductResponseDto>> findAll() {
        return ResponseEntity.ok().body(productService.findAll());
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ProductRequestDto product) {
        productService.updateProduct(id, product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product successfully updated.");
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product successfully deleted.");
    }
}

