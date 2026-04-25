package com.example.menunfl.service;

import com.example.menunfl.dto.product.ProductRequestDto;
import com.example.menunfl.dto.product.ProductResponseDto;
import com.example.menunfl.entity.product.Product;
import com.example.menunfl.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDto create(ProductRequestDto data) {

        if (productRepository.existsByNameIgnoreCase(data.name())) {
            throw new RuntimeException("Produto já existe");
        }

        Product product = toEntity(data);

        Product saved = productRepository.save(product);

        return ProductResponseDto.fromEntity(saved);
    }

    public void updateProduct(Long id, ProductRequestDto data) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new RuntimeException("Product not found.");
        }
        product.setName(data.name());
        product.setPrice(data.price());
        product.setCategory(data.category());
        product.setImage(data.image());
        productRepository.save(product);
    }

    public List<ProductResponseDto> findAll(){
        return productRepository.findAll().stream().map(ProductResponseDto::fromEntity).toList();
    }

    private Product toEntity(ProductRequestDto data) {
        Product product = new Product();
        product.setName(data.name());
        product.setPrice(data.price());
        product.setCategory(data.category());
        product.setImage(
                !Objects.equals(data.image(), "") ? data.image() : imageProductDefault()
        );
        product.setActive(true);
        if(!data.stockControlled()) {
            product.setStockControlled(false);
            product.setStock(0);
        }else{
            product.setStockControlled(true);
            product.setStock(data.stock());
        }
        return product;
    }

    private String imageProductDefault(){
        var product = productRepository.findByNameIgnoreCase("product default");
        if(product.isEmpty()){
            throw new RuntimeException("product not found");
        }
        return product.get().getImage();
    }

    public void deleteProduct(Long idProduct) {
        productRepository.deleteById(idProduct);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }

}
