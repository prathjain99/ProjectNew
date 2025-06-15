package com.quantcrux.auth.service;

import com.quantcrux.auth.dto.AuthResponse;
import com.quantcrux.auth.dto.LoginRequest;
import com.quantcrux.auth.dto.RegisterRequest;
import com.quantcrux.auth.entity.Role;
import com.quantcrux.auth.entity.User;
import com.quantcrux.auth.repository.UserRepository;
import com.quantcrux.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole().toString());
        
        AuthResponse.UserDto userDto = new AuthResponse.UserDto(
                user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getName()
        );
        
        return new AuthResponse(token, userDto);
    }
    
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole() != null ? request.getRole() : Role.CLIENT,
                request.getName()
        );
        
        user = userRepository.save(user);
        
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole().toString());
        
        AuthResponse.UserDto userDto = new AuthResponse.UserDto(
                user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getName()
        );
        
        return new AuthResponse(token, userDto);
    }
    
    // Method to create demo users if they don't exist
    public void createDemoUsersIfNotExist() {
        createDemoUserIfNotExists("client1", "client1@quantcrux.com", "Client User", Role.CLIENT);
        createDemoUserIfNotExists("pm1", "pm1@quantcrux.com", "Portfolio Manager", Role.PORTFOLIO_MANAGER);
        createDemoUserIfNotExists("researcher1", "researcher1@quantcrux.com", "Researcher User", Role.RESEARCHER);
    }
    
    private void createDemoUserIfNotExists(String username, String email, String name, Role role) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User(
                    username,
                    email,
                    passwordEncoder.encode("password"),
                    role,
                    name
            );
            userRepository.save(user);
        }
    }
}