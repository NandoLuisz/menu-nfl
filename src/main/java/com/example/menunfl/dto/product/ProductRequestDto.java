package com.example.menunfl.dto.product;

import com.example.menunfl.entity.enums.Category;

import java.math.BigDecimal;

public record ProductRequestDto(
        String name,
        BigDecimal price,
        Category category,
        String image,
        boolean stockControlled,
        Integer stock)  {
}
