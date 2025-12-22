package com.miqdad.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
    private Long productId;
    private Integer quantity;
}
