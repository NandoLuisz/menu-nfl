package com.example.menunfl.dto;

import com.example.menunfl.entity.CATEGORY;

import java.math.BigDecimal;

public record ProductRequest(String name, String description, BigDecimal price, CATEGORY category)  {
}
