package com.miqdad.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                   // id : int

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;                    // user_id : int

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;       // total_amount : decimal(10,2)

    @Column(name = "shipping_address", columnDefinition = "text")
    private String shippingAddress;       // shipping_address : text

    @Column(length = 50)
    private String phone;                 // phone : varchar(50)

    @Column(name = "created_at")
    private LocalDateTime createdAt;      // created_at : timestamp
    
    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
