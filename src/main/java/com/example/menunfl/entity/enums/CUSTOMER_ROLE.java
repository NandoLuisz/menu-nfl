package com.example.menunfl.entity.enums;

import lombok.Getter;

@Getter
public enum CUSTOMER_ROLE {
    ADMIN("admin"),
    CUSTOMER("customer");

    private String role;

    CUSTOMER_ROLE(String role){
        this.role = role;
    }

}
