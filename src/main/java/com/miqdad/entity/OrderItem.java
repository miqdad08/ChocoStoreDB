package com.miqdad.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // id : int

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;              // order_id : int

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;          // product_id : int

    private Integer quantity;         // quantity : int

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;         // price : decimal(10,2)
}
