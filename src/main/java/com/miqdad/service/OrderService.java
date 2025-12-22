package com.miqdad.service;

import com.miqdad.entity.Order;

import java.util.List;

public interface OrderService {

    Order createOrderFromCart(Long userId, String shippingAddress, String phone);

    List<Order> getOrdersByUser(Long userId);

    Order getOrderByIdForUser(Long orderId, Long userId);
}
