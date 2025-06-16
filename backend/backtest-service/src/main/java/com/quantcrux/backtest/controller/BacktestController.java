package com.quantcrux.backtest.controller;

import com.quantcrux.backtest.dto.BacktestRequest;
import com.quantcrux.backtest.dto.BacktestResponse;
import com.quantcrux.backtest.service.BacktestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backtest")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class BacktestController {
    
    @Autowired
    private BacktestService backtestService;
    
    @PostMapping
    public ResponseEntity<BacktestResponse> runBacktest(
            @RequestBody BacktestRequest request,
            @RequestHeader("X-User-Id") String userId) {
        try {
            BacktestResponse response = backtestService.runBacktest(request, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Backtest failed: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<BacktestResponse>> getBacktestHistory(
            @RequestHeader("X-User-Id") String userId) {
        List<BacktestResponse> history = backtestService.getBacktestHistory(userId);
        return ResponseEntity.ok(history);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Backtest service is running");
    }
}