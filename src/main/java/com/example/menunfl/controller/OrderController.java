package com.example.menunfl.controller;

import com.example.menunfl.dto.order.OrderRequestDto;
import com.example.menunfl.dto.order.OrderResponseDto;
import com.example.menunfl.entity.address.Address;
import com.example.menunfl.entity.order.Order;
import com.example.menunfl.entity.order.OrderItem;
import com.example.menunfl.entity.product.Product;
import com.example.menunfl.service.AddressService;
import com.example.menunfl.service.UserService;
import com.example.menunfl.service.OrderService;
import com.example.menunfl.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/order")
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;
    private final AddressService addressService;

    public OrderController(OrderService orderService, ProductService productService, UserService userService, AddressService addressService) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
        this.addressService = addressService;
    }

    @PostMapping("/place-order")
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody OrderRequestDto data, JwtAuthenticationToken token) {
        var user = userService.findById(UUID.fromString(token.getName()));

        Address address = addressService.getAllAddressesById(user.getId()).getFirst();

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);

        for(var itemDto : data.orderItemsList()){
            Product product = productService.getProduct(itemDto.productId());

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.quantity());
            orderItem.setPrice(product.getPrice());

            order.addItem(orderItem);

            product.decreaseStock(itemDto.quantity());
        }

        order.calculateTotal();

        Order savedOrder = orderService.saveOrder(order);

        return ResponseEntity.ok(OrderResponseDto.fromEntity(savedOrder));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll().stream().map(OrderResponseDto::fromEntity).toList());
    }
}
