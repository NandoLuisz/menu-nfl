package com.example.menunfl.service;

import com.example.menunfl.dto.ProductRequestDto;
import com.example.menunfl.dto.ProductResponseDto;
import com.example.menunfl.entity.product.Product;
import com.example.menunfl.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDto createProduct(ProductRequestDto data) {

        if (productRepository.existsByNameIgnoreCase(data.name())) {
            throw new RuntimeException("Produto já existe");
        }

        Product product = toEntity(data);

        Product saved = productRepository.save(product);

        return ProductResponseDto.fromEntity(saved);
    }

    public void updateProduct(Product data) {
        var product = productRepository.findById(data.getId()).orElse(null);
        if (product == null) {
            throw new RuntimeException("Product not found.");
        }
        product.setName(data.getName());
        product.setDescription(data.getDescription());
        product.setPrice(data.getPrice());
        product.setCategory(data.getCategory());
        product.setImage(data.getImage());
        productRepository.save(product);
    }

    private Product toEntity(ProductRequestDto data) {
        Product product = new Product();
        product.setName(data.name());
        product.setDescription(data.description());
        product.setPrice(data.price());
        product.setCategory(data.category());
        product.setImage(
                data.image() != null ? data.image() : imageProductDefault()
        );
        product.setActive(true);
        product.setStock(0);
        return product;
    }

    private String imageProductDefault(){
        var product = productRepository.findByNameIgnoreCase("product default");
        if(product.isEmpty()){
            throw new RuntimeException("product not found");
        }
        return product.get().getImage();
    }
}
