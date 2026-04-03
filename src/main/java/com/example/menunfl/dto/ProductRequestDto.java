package com.example.menunfl.dto;

import com.example.menunfl.entity.CATEGORY;

import java.math.BigDecimal;

public record ProductRequestDto(String name, String description, BigDecimal price, String image, CATEGORY category)  {
}
