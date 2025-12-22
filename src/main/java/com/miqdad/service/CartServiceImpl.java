package com.miqdad.service;


import com.miqdad.entity.CartRequest;
import com.miqdad.entity.Cart;
import com.miqdad.entity.Product;
import com.miqdad.entity.User;
import com.miqdad.repository.CartRepository;
import com.miqdad.repository.ProductRepository;
import com.miqdad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Cart> getMyCart(String userEmail) {
        User user = getUserByEmail(userEmail);
        return cartRepository.findByUserId(user.getId());
    }

    @Override
    public Cart addToCart(String userEmail, CartRequest request) {
        User user = getUserByEmail(userEmail);
        Product product = getProductById(request.getProductId());

        Cart cart = Cart.builder()
                .user(user)
                .product(product)
                .quantity(request.getQuantity())
                .build();

        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCartItem(String userEmail, Long cartId, CartRequest request) {
        User user = getUserByEmail(userEmail);

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Cannot modify other user's cart");
        }

        Product product = getProductById(request.getProductId());

        cart.setProduct(product);
        cart.setQuantity(request.getQuantity());

        return cartRepository.save(cart);
    }

    @Override
    public void removeCartItem(String userEmail, Long cartId) {
        User user = getUserByEmail(userEmail);

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Cannot delete other user's cart");
        }

        cartRepository.delete(cart);
    }

    @Override
    public void clearMyCart(String userEmail) {
        User user = getUserByEmail(userEmail);
        List<Cart> carts = cartRepository.findByUserId(user.getId());
        cartRepository.deleteAll(carts);
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
