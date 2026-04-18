package com.example.menunfl.dto.order;

import com.example.menunfl.entity.order.Order;
import com.example.menunfl.entity.order.OrderItem;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        String productName,
        Integer quantity,
        BigDecimal price,
        BigDecimal subtotal
) {
    public static OrderItemResponseDto fromEntity(OrderItem item) {
        return new OrderItemResponseDto(
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getSubtotal()
        );
    }
}
