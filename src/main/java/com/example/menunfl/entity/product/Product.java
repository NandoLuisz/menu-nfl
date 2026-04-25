package com.example.menunfl.entity.product;

import com.example.menunfl.entity.enums.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    private String image;

    @Column(nullable = false)
    private boolean stockControlled;

    @Column(nullable = false)
    private Integer stock = 0;

    @Column(nullable = false)
    private boolean active = true;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        validateStockState();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        validateStockState();
    }

    private void validateStockState() {
        // Garante consistência: se não controla estoque, estoque sempre 0
        if (!this.stockControlled) {
            this.stock = 0;
        }

        // Evita null (segurança extra)
        if (this.stock == null) {
            this.stock = 0;
        }

        // Evita valores negativos
        if (this.stock < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }
    }

    public void decreaseStock(int amount) {

        if (!this.stockControlled) {
            return;
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        if (this.stock < amount) {
            throw new IllegalArgumentException("Estoque insuficiente");
        }

        this.stock -= amount;
    }

    public boolean hasStockControl() {
        return this.stockControlled;
    }
}