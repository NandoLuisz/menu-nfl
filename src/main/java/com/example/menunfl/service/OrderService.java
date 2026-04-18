package com.example.menunfl.service;

import com.example.menunfl.entity.order.Order;
import com.example.menunfl.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
