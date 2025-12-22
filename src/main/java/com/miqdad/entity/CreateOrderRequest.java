package com.miqdad.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {
    private String shippingAddress;
    private String phone;
}
