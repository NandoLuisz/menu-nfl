package com.example.menunfl.dto.product;

import com.example.menunfl.entity.enums.Category;
import com.example.menunfl.entity.product.Product;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        BigDecimal price,
        Category category,
        String image,
        Integer stock,
        boolean stockControlled
) {

    public static ProductResponseDto fromEntity(Product data) {
        return new ProductResponseDto(
                data.getId(),
                data.getName(),
                data.getPrice(),
                data.getCategory(),
                data.getImage(),
                data.getStock(),
                data.isStockControlled()
        );
    }
}
