package com.quantcrux.strategy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantcrux.strategy.dto.StrategyRequest;
import com.quantcrux.strategy.entity.Strategy;
import com.quantcrux.strategy.repository.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrategyService {
    
    @Autowired
    private StrategyRepository strategyRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public List<Strategy> getStrategiesByUser(String userId) {
        return strategyRepository.findByCreatedBy(userId);
    }
    
    public Strategy createStrategy(StrategyRequest request, String userId) {
        try {
            String indicatorsJson = objectMapper.writeValueAsString(request.getIndicators());
            String rulesJson = objectMapper.writeValueAsString(request.getRules());
            
            Strategy strategy = new Strategy(
                    request.getName(),
                    request.getDescription(),
                    userId,
                    request.getAssetList(),
                    indicatorsJson,
                    rulesJson
            );
            
            return strategyRepository.save(strategy);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create strategy", e);
        }
    }
    
    public Strategy getStrategyById(String id) {
        return strategyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Strategy not found"));
    }
}