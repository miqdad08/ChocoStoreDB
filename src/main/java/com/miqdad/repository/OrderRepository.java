package com.miqdad.repository;

import com.miqdad.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // semua order milik user tertentu
    List<Order> findByUserId(Long userId);
}
