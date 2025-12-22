package com.miqdad.service;

import com.miqdad.entity.CartRequest;
import com.miqdad.entity.Cart;

import java.util.List;

public interface CartService {

    List<Cart> getMyCart(String userEmail);

    Cart addToCart(String userEmail, CartRequest request);

    Cart updateCartItem(String userEmail, Long cartId, CartRequest request);

    void removeCartItem(String userEmail, Long cartId);

    void clearMyCart(String userEmail);
}
