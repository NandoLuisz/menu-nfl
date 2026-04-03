package com.example.menunfl.entity.address;

import com.example.menunfl.entity.customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "addresses_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String street;

    @NotBlank
    @Column(nullable = false)
    private String number;

    private String complement;

    @NotBlank
    @Column(nullable = false)
    private String neighborhood;

    @NotBlank
    @Column(nullable = false)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private STATES state;

    @NotBlank
    @Column(nullable = false, length = 10)
    private String zip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private Boolean isDefault = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        normalize();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    private void normalize() {
        this.street = this.street.trim();
        this.city = this.city.trim();
    }
}