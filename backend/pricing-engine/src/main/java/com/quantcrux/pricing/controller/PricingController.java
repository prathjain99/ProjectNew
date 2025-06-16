package com.quantcrux.pricing.controller;

import com.quantcrux.pricing.dto.PricingRequest;
import com.quantcrux.pricing.dto.PricingResponse;
import com.quantcrux.pricing.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pricing")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class PricingController {
    
    @Autowired
    private PricingService pricingService;
    
    @PostMapping("/calculate")
    public ResponseEntity<PricingResponse> calculatePrice(@RequestBody PricingRequest request) {
        try {
            PricingResponse response = pricingService.calculatePrice(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Pricing calculation failed: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/monte-carlo")
    public ResponseEntity<PricingResponse> monteCarloPrice(@RequestBody PricingRequest request) {
        try {
            PricingResponse response = pricingService.monteCarloPrice(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Monte Carlo pricing failed: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Pricing engine is running");
    }
}