package com.ecommerce.controller;

import com.ecommerce.entity.CartItem;
import com.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public List<CartItem> getCart() {
        return cartService.getCartItems();
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<CartItem> addToCart(@PathVariable Long productId,
            @RequestParam(defaultValue = "1") int quantity) {
        return ResponseEntity.ok(cartService.addToCart(productId, quantity));
    }

    @PutMapping("/update/{itemId}")
    public ResponseEntity<CartItem> updateQuantity(@PathVariable Long itemId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateQuantity(itemId, quantity));
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }
}
