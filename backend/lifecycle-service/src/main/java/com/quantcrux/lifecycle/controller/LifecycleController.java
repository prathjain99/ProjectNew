package com.quantcrux.lifecycle.controller;

import com.quantcrux.lifecycle.dto.LifecycleEventResponse;
import com.quantcrux.lifecycle.service.LifecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lifecycle")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class LifecycleController {
    
    @Autowired
    private LifecycleService lifecycleService;
    
    @GetMapping("/events/{tradeId}")
    public ResponseEntity<List<LifecycleEventResponse>> getTradeEvents(@PathVariable String tradeId) {
        List<LifecycleEventResponse> events = lifecycleService.getTradeEvents(tradeId);
        return ResponseEntity.ok(events);
    }
    
    @PostMapping("/process-fixings")
    public ResponseEntity<String> processFixings() {
        try {
            lifecycleService.processScheduledFixings();
            return ResponseEntity.ok("Fixings processed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to process fixings: " + e.getMessage());
        }
    }
    
    @PostMapping("/check-barriers")
    public ResponseEntity<String> checkBarriers() {
        try {
            lifecycleService.checkBarrierBreaches();
            return ResponseEntity.ok("Barrier checks completed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to check barriers: " + e.getMessage());
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Lifecycle service is running");
    }
}