package com.example.menunfl.dto.order;

import com.example.menunfl.dto.address.AddressResponseDto;
import com.example.menunfl.dto.customer.CustomerResponseDto;
import com.example.menunfl.entity.order.Order;
import com.example.menunfl.entity.order.OrderItem;

import java.util.List;

public record OrderResponseDto(
        CustomerResponseDto customer,
        List<OrderItemResponseDto> items,
        AddressResponseDto address
) {

    public static OrderResponseDto fromEntity(Order order) {
        return new OrderResponseDto(
                CustomerResponseDto.fromEntity(order.getCustomer()),
                order.getItems().stream()
                        .map(OrderItemResponseDto::fromEntity)
                        .toList(),
                AddressResponseDto.fromEntity(order.getAddress())
        );
    }
}
