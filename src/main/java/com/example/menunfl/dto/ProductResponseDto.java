package com.example.menunfl.dto;

import com.example.menunfl.entity.CATEGORY;
import com.example.menunfl.entity.product.Product;
import com.example.menunfl.service.ProductService;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        CATEGORY category,
        String image
) {

    public static ProductResponseDto fromEntity(Product data) {
        return new ProductResponseDto(
                data.getId(),
                data.getName(),
                data.getDescription(),
                data.getPrice(),
                data.getCategory(),
                data.getImage()
        );
    }
}
