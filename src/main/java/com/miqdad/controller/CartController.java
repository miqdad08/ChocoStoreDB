package com.miqdad.controller;

import com.miqdad.entity.CartRequest;
import com.miqdad.entity.Cart;
import com.miqdad.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // GET /api/cart -> list cart user yang login
    @GetMapping
    public List<Cart> getMyCart(Authentication authentication) {
        String email = authentication.getName();
        return cartService.getMyCart(email);
    }

    // POST /api/cart -> tambah item ke cart
    @PostMapping
    public Cart addToCart(@RequestBody CartRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        return cartService.addToCart(email, request);
    }

    // PUT /api/cart/{id} -> update item cart
    @PutMapping("/{id}")
    public Cart updateCartItem(@PathVariable Long id,
            @RequestBody CartRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        return cartService.updateCartItem(email, id, request);
    }

    // DELETE /api/cart/{id} -> hapus satu item
    @DeleteMapping("/{id}")
    public void deleteCartItem(@PathVariable Long id,
            Authentication authentication) {
        String email = authentication.getName();
        cartService.removeCartItem(email, id);
    }

    // DELETE /api/cart -> kosongkan cart user
    @DeleteMapping
    public void clearCart(Authentication authentication) {
        String email = authentication.getName();
        cartService.clearMyCart(email);
    }
}
