package com.quantcrux.strategy.controller;

import com.quantcrux.strategy.dto.StrategyRequest;
import com.quantcrux.strategy.entity.Strategy;
import com.quantcrux.strategy.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/strategies")
@CrossOrigin(origins = "http://localhost:5173")
public class StrategyController {
    
    @Autowired
    private StrategyService strategyService;
    
    @GetMapping
    public ResponseEntity<List<Strategy>> getStrategies(@RequestHeader("X-User-Id") String userId) {
        List<Strategy> strategies = strategyService.getStrategiesByUser(userId);
        return ResponseEntity.ok(strategies);
    }
    
    @PostMapping
    public ResponseEntity<Strategy> createStrategy(@RequestBody StrategyRequest request, 
                                                  @RequestHeader("X-User-Id") String userId) {
        Strategy strategy = strategyService.createStrategy(request, userId);
        return ResponseEntity.ok(strategy);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Strategy> getStrategy(@PathVariable String id) {
        Strategy strategy = strategyService.getStrategyById(id);
        return ResponseEntity.ok(strategy);
    }
}