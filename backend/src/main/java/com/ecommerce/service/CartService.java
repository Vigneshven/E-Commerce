package com.ecommerce.service;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public CartItem addToCart(Long productId, int quantity) {
        User user = getCurrentUser();
        Product product = productRepository.findById(productId).orElseThrow();
        Optional<CartItem> existing = cartItemRepository.findByUserAndProduct(user, product);
        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartItemRepository.save(item);
        }
        CartItem item = new CartItem();
        item.setUser(user);
        item.setProduct(product);
        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }

    public List<CartItem> getCartItems() {
        return cartItemRepository.findByUser(getCurrentUser());
    }

    public CartItem updateQuantity(Long itemId, int quantity) {
        CartItem item = cartItemRepository.findById(itemId).orElseThrow();
        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }

    public void removeItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }

    public void clearCart() {
        User user = getCurrentUser();
        cartItemRepository.deleteAllByUser(user);
    }
}
