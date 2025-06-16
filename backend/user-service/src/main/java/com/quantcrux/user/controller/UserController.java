package com.quantcrux.user.controller;

import com.quantcrux.user.entity.UserProfile;
import com.quantcrux.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getUserProfile(@RequestHeader("X-User-Id") String userId) {
        UserProfile profile = userService.getUserProfile(userId);
        return ResponseEntity.ok(profile);
    }
    
    @PostMapping("/profile")
    public ResponseEntity<UserProfile> createOrUpdateProfile(
            @RequestBody UserProfile profile,
            @RequestHeader("X-User-Id") String userId) {
        profile.setUserId(userId);
        UserProfile savedProfile = userService.saveUserProfile(profile);
        return ResponseEntity.ok(savedProfile);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("User service is running");
    }
}