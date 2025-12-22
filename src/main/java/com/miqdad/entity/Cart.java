package com.miqdad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;              // id : int

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore                      // supaya field user tidak muncul di JSON
    private User user;               // user_id : int

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;         // product_id : int

    private Integer quantity;        // quantity : int
}
