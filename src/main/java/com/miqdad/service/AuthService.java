package com.miqdad.service;


import com.miqdad.entity.LoginRequest;
import com.miqdad.entity.RegisterRequest;
import com.miqdad.entity.AuthResponse;

public interface AuthService {

    AuthResponse registerUser(RegisterRequest request); // ROLE_USER

    AuthResponse registerAdmin(RegisterRequest request); // ROLE_ADMIN (opsional, bisa hanya lewat SQL)

    AuthResponse login(LoginRequest request);
}
