package com.miqdad.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String fullName;
    private String password;
    private String birthDate; // format: 2003-01-15
}



