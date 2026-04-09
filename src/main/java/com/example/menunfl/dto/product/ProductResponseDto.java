package com.example.menunfl.dto.product;

import com.example.menunfl.entity.enums.CATEGORY;
import com.example.menunfl.entity.product.Product;

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
