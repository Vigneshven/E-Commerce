package com.ecommerce.controller;

import com.ecommerce.entity.OrderEntity;
import com.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderEntity> getOrders() {
        return orderService.getOrdersForCurrentUser();
    }

    @PostMapping("/place")
    public ResponseEntity<OrderEntity> placeOrder(@RequestParam String address, @RequestParam String paymentMethod) {
        return ResponseEntity.ok(orderService.placeOrder(address, paymentMethod));
    }

    @GetMapping("/all")
    public List<OrderEntity> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderEntity> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
}
