package com.quantcrux.auth.dto;

import com.quantcrux.auth.entity.Role;

public class AuthResponse {
    private String token;
    private UserDto user;
    
    // Constructors
    public AuthResponse() {}
    
    public AuthResponse(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }
    
    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
    
    public static class UserDto {
        private String id;
        private String username;
        private String email;
        private Role role;
        private String name;
        
        public UserDto() {}
        
        public UserDto(String id, String username, String email, Role role, String name) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.role = role;
            this.name = name;
        }
        
        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public Role getRole() { return role; }
        public void setRole(Role role) { this.role = role; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}