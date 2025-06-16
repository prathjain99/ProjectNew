package com.quantcrux.portfolio.controller;

import com.quantcrux.portfolio.dto.PortfolioSummary;
import com.quantcrux.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolio")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class PortfolioController {
    
    @Autowired
    private PortfolioService portfolioService;
    
    @GetMapping
    public ResponseEntity<PortfolioSummary> getPortfolio(@RequestHeader("X-User-Id") String userId) {
        PortfolioSummary portfolio = portfolioService.getPortfolioSummary(userId);
        return ResponseEntity.ok(portfolio);
    }
    
    @PostMapping("/positions")
    public ResponseEntity<String> createPosition(
            @RequestParam String productId,
            @RequestParam Double quantity,
            @RequestParam Double entryPrice,
            @RequestHeader("X-User-Id") String userId) {
        try {
            portfolioService.createPosition(userId, productId, quantity, entryPrice);
            return ResponseEntity.ok("Position created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create position: " + e.getMessage());
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Portfolio service is running");
    }
}