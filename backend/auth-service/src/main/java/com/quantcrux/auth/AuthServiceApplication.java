package com.quantcrux.auth;

import com.quantcrux.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AuthServiceApplication implements CommandLineRunner {
    
    @Autowired
    private AuthService authService;
    
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Create demo users on startup
        authService.createDemoUsersIfNotExist();
        System.out.println("Demo users created/verified successfully");
    }
}