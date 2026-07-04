package com.ecommerce.service;

import com.ecommerce.entity.OrderEntity;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public OrderEntity placeOrder(String address, String paymentMethod) {
        User user = getCurrentUser();
        List<com.ecommerce.entity.CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setShippingAddress(address);
        order.setPaymentMethod(paymentMethod);
        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;
        for (com.ecommerce.entity.CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(itemTotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());
            order.getItems().add(orderItem);
        }
        order.setTotalAmount(total.add(BigDecimal.valueOf(5)));
        orderRepository.save(order);
        cartItemRepository.deleteAllByUser(user);
        return order;
    }

    public List<OrderEntity> getOrdersForCurrentUser() {
        return orderRepository.findByUser(getCurrentUser());
    }

    public OrderEntity updateOrderStatus(Long orderId, String status) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }
}
