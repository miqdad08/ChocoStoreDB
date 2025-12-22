package com.miqdad.service;

import com.miqdad.entity.Cart;
import com.miqdad.entity.Order;
import com.miqdad.entity.OrderItem;
import com.miqdad.entity.User;
import com.miqdad.repository.CartRepository;
import com.miqdad.repository.OrderItemRepository;
import com.miqdad.repository.OrderRepository;
import com.miqdad.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public Order createOrderFromCart(Long userId, String shippingAddress, String phone) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Cart> carts = cartRepository.findByUserId(userId);
        if (carts.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal totalAmount = carts.stream()
                .map(c -> c.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(c.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .user(user)
                .totalAmount(totalAmount)
                .shippingAddress(shippingAddress)
                .phone(phone)
                .createdAt(LocalDateTime.now())  // atau pakai @PrePersist
                .build();

        order = orderRepository.save(order);

        for (Cart cart : carts) {
            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(cart.getProduct())
                    .quantity(cart.getQuantity())
                    .price(cart.getProduct().getPrice())
                    .build();
            orderItemRepository.save(item);
        }

        cartRepository.deleteAll(carts);

        return order;
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Order getOrderByIdForUser(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Forbidden");
        }
        return order;
    }
}
