package com.example.menunfl.entity.order;

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
@Table(name = "orders")
public class Order {
    @Id
    private Long id;
    private String description;
    private double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
