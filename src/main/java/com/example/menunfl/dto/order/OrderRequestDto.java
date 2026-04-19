package com.example.menunfl.dto.order;

import java.util.List;

public record OrderRequestDto(List<OrderItemRequest> orderItemsList, String customerEmail) {

}
