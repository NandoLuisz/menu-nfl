package com.example.menunfl.dto.order;

import java.util.List;
import java.util.UUID;

public record OrderRequestDto(List<OrderItemRequest> orderItemsList, UUID customerId, Long addressId) {

}
