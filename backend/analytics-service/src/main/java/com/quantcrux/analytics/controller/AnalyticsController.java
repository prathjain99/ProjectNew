package com.quantcrux.analytics.controller;

import com.quantcrux.analytics.dto.RiskMetrics;
import com.quantcrux.analytics.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class AnalyticsController {
    
    @Autowired
    private AnalyticsService analyticsService;
    
    @GetMapping("/risk-metrics")
    public ResponseEntity<RiskMetrics> getRiskMetrics(@RequestHeader("X-User-Id") String userId) {
        RiskMetrics metrics = analyticsService.calculateRiskMetrics(userId);
        return ResponseEntity.ok(metrics);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Analytics service is running");
    }
}