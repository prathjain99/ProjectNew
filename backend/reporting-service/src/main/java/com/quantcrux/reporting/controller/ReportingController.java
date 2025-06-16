package com.quantcrux.reporting.controller;

import com.quantcrux.reporting.dto.ReportRequest;
import com.quantcrux.reporting.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ReportingController {
    
    @Autowired
    private ReportingService reportingService;
    
    @PostMapping("/generate")
    public ResponseEntity<String> generateReport(
            @RequestBody ReportRequest request,
            @RequestHeader("X-User-Id") String userId) {
        try {
            String reportId = reportingService.generateReport(request, userId);
            return ResponseEntity.ok(reportId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate report: " + e.getMessage());
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Reporting service is running");
    }
}