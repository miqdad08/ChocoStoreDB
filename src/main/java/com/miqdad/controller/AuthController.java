package com.miqdad.controller;

import com.miqdad.entity.AuthResponse;
import com.miqdad.entity.LoginRequest;
import com.miqdad.entity.RegisterRequest;
import com.miqdad.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Register user biasa
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.registerUser(request);
    }

    // Register admin (bisa dibatasi hanya boleh dipanggil ADMIN via SecurityConfig)
    @PostMapping("/register-admin")
    public AuthResponse registerAdmin(@RequestBody RegisterRequest request) {
        return authService.registerAdmin(request);
    }

    // Login
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
