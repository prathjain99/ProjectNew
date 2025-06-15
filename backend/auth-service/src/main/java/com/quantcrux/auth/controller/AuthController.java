package com.quantcrux.auth.controller;

import com.quantcrux.auth.dto.AuthResponse;
import com.quantcrux.auth.dto.LoginRequest;
import com.quantcrux.auth.dto.RegisterRequest;
import com.quantcrux.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("Login attempt for user: " + request.getUsername());
            AuthResponse response = authService.login(request);
            System.out.println("Login successful for user: " + request.getUsername());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Login failed for user: " + request.getUsername() + ", error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Registration failed: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth service is running");
    }
}