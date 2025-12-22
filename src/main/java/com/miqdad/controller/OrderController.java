package com.miqdad.controller;

import com.miqdad.entity.Order;
import com.miqdad.entity.User;
import com.miqdad.entity.CreateOrderRequest;
import com.miqdad.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
public Order createOrder(@AuthenticationPrincipal User user,
                         @RequestBody CreateOrderRequest request) {
    return orderService.createOrderFromCart(
            user.getId(),
            request.getShippingAddress(),
            request.getPhone()
    );
}


    @GetMapping
    public List<Order> getMyOrders(@AuthenticationPrincipal User user) {
        return orderService.getOrdersByUser(user.getId());
    }

    @GetMapping("/{id}")
    public Order getOrderDetail(@PathVariable Long id,
                                @AuthenticationPrincipal User user) {
        return orderService.getOrderByIdForUser(id, user.getId());
    }
}
