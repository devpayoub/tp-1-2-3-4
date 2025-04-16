package com.gestion_shiop.shop.controller;

import com.gestion_shiop.shop.models.*;
import com.gestion_shiop.shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth/member")
public class MemberController {
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private CartItemRepository cartRepo;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @PostMapping("/cart")
    public CartItem addToCart(@RequestBody Map<String, Long> payload) {
        Long productId = payload.get("productId");
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem item = new CartItem();
        item.setProduct(product);
        return cartRepo.save(item);
    }

    @GetMapping("/cart")
    public List<CartItem> viewCart() {
        return cartRepo.findAll();
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id) {
        if (!cartRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cartRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cart/{id}")
    public ResponseEntity<CartItem> updateCartItemQuantity(@PathVariable Long id, @RequestBody Map<String, Integer> payload) {
        int quantity = payload.get("quantity");

        return cartRepo.findById(id)
                .map(cartItem -> {
                    //cartItem.setQuantity(quantity); // Assuming you have a quantity field in CartItem
                    return ResponseEntity.ok(cartRepo.save(cartItem));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/orders")
    public String placeOrder() {
        return "Order placed successfully";
    }
}
