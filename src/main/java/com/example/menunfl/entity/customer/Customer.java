package com.example.menunfl.entity.customer;

import com.example.menunfl.entity.address.Address;
import com.example.menunfl.entity.enums.CUSTOMER_ROLE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Email
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String phone;

    @Past
    private LocalDate birthday;

    @Column(nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CUSTOMER_ROLE customerRole;

    @JsonManagedReference
    @OneToMany(mappedBy = "customer",  cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Customer(String name, String email, String phone, String encryptedPassword, CUSTOMER_ROLE customerRole) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = encryptedPassword;
        this.customerRole = customerRole;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.email = this.email.toLowerCase().trim();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addAddress(Address address) {
        address.setCustomer(this);
        this.addresses.add(address);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.customerRole == CUSTOMER_ROLE.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}