package com.ecommerce.controller;

import com.ecommerce.entity.User;
import com.ecommerce.entity.WishlistItem;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.repository.WishlistItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistItemRepository wishlistItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    @GetMapping
    public List<WishlistItem> getWishlist() {
        return wishlistItemRepository.findByUser(getCurrentUser());
    }

    @PostMapping("/{productId}")
    public ResponseEntity<WishlistItem> addToWishlist(@PathVariable Long productId) {
        User user = getCurrentUser();
        WishlistItem item = new WishlistItem();
        item.setUser(user);
        item.setProduct(productRepository.findById(productId).orElseThrow());
        return ResponseEntity.ok(wishlistItemRepository.save(item));
    }
}
