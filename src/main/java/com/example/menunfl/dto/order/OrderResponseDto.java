package com.example.menunfl.dto.order;

import com.example.menunfl.dto.address.AddressResponseDto;
import com.example.menunfl.dto.user.UserResponseDto;
import com.example.menunfl.entity.order.Order;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDto(
        UserResponseDto customer,
        List<OrderItemResponseDto> items,
        AddressResponseDto address,
        BigDecimal total
) {

    public static OrderResponseDto fromEntity(Order order) {
        return new OrderResponseDto(
                UserResponseDto.fromEntity(order.getUser()),
                order.getItems().stream()
                        .map(OrderItemResponseDto::fromEntity)
                        .toList(),
                AddressResponseDto.fromEntity(order.getAddress()),
                order.getTotal()
        );
    }
}
