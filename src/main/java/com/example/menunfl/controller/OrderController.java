package com.example.menunfl.controller;

import com.example.menunfl.dto.order.OrderRequestDto;
import com.example.menunfl.dto.order.OrderResponseDto;
import com.example.menunfl.entity.address.Address;
import com.example.menunfl.entity.customer.Customer;
import com.example.menunfl.entity.order.Order;
import com.example.menunfl.entity.order.OrderItem;
import com.example.menunfl.entity.product.Product;
import com.example.menunfl.service.AddressService;
import com.example.menunfl.service.CustomerService;
import com.example.menunfl.service.OrderService;
import com.example.menunfl.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/order")
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final AddressService addressService;

    public OrderController(OrderService orderService, ProductService productService, CustomerService customerService, AddressService addressService) {
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
        this.addressService = addressService;
    }

    @PostMapping("place-order")
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody OrderRequestDto data) {
        Customer customer = customerService.getCustomerByEmail(data.customerEmail());

        Address address = addressService.getAllAddressesById(customer.getId()).getFirst();

        Order order = new Order();
        order.setCustomer(customer);
        order.setAddress(address);

        for(var itemDto : data.orderItemsList()){
            Product product = productService.getProduct(itemDto.productId());

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.quantity());
            orderItem.setPrice(product.getPrice());

            order.addItem(orderItem);

            product.decreaseStock(itemDto.quantity(), product.getCategory());
        }

        order.calculateTotal();

        Order savedOrder = orderService.saveOrder(order);

        return ResponseEntity.ok(OrderResponseDto.fromEntity(savedOrder));
    }
}
