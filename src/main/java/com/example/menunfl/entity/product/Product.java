package com.example.menunfl.entity.product;

import com.example.menunfl.entity.CATEGORY_STOCK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    private Long id;
    private String name;
    private String description;
    private double price;
    private CATEGORY_STOCK category;
    private String image;
    private Boolean active;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
