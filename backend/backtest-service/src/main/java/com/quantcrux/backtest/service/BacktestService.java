package com.quantcrux.backtest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantcrux.backtest.dto.BacktestRequest;
import com.quantcrux.backtest.dto.BacktestResponse;
import com.quantcrux.backtest.entity.BacktestResult;
import com.quantcrux.backtest.repository.BacktestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BacktestService {
    
    @Autowired
    private BacktestResultRepository backtestResultRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public BacktestResponse runBacktest(BacktestRequest request, String userId) {
        try {
            // Simulate backtest execution
            BacktestResult result = executeBacktest(request, userId);
            
            // Save result to database
            BacktestResult savedResult = backtestResultRepository.save(result);
            
            // Convert to response
            return convertToResponse(savedResult);
        } catch (Exception e) {
            throw new RuntimeException("Failed to run backtest", e);
        }
    }
    
    public List<BacktestResponse> getBacktestHistory(String userId) {
        List<BacktestResult> results = backtestResultRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return results.stream()
                .map(this::convertToResponse)
                .toList();
    }
    
    private BacktestResult executeBacktest(BacktestRequest request, String userId) {
        // Mock backtest simulation
        Random random = new Random();
        
        // Generate mock equity curve
        List<Map<String, Object>> equityCurve = generateMockEquityCurve(
                request.getStartDate(), 
                request.getEndDate(), 
                request.getInitialCapital()
        );
        
        // Calculate performance metrics
        double finalValue = request.getInitialCapital() * (1 + (random.nextGaussian() * 0.2 + 0.1));
        double totalReturn = (finalValue - request.getInitialCapital()) / request.getInitialCapital();
        double sharpeRatio = 1.2 + random.nextGaussian() * 0.5;
        double maxDrawdown = -0.05 - random.nextDouble() * 0.15;
        int totalTrades = 50 + random.nextInt(100);
        int profitableTrades = (int) (totalTrades * (0.4 + random.nextDouble() * 0.4));
        double winRate = (double) profitableTrades / totalTrades;
        
        BacktestResult result = new BacktestResult();
        result.setStrategyId(request.getStrategyId());
        result.setUserId(userId);
        result.setSymbol(request.getSymbol());
        result.setStartDate(request.getStartDate());
        result.setEndDate(request.getEndDate());
        result.setInitialCapital(request.getInitialCapital());
        result.setFinalValue(finalValue);
        result.setTotalReturn(totalReturn);
        result.setSharpeRatio(sharpeRatio);
        result.setMaxDrawdown(maxDrawdown);
        result.setWinRate(winRate);
        result.setTotalTrades(totalTrades);
        result.setProfitableTrades(profitableTrades);
        
        try {
            result.setEquityCurveJson(objectMapper.writeValueAsString(equityCurve));
        } catch (Exception e) {
            result.setEquityCurveJson("[]");
        }
        
        return result;
    }
    
    private List<Map<String, Object>> generateMockEquityCurve(String startDate, String endDate, Double initialCapital) {
        List<Map<String, Object>> curve = new ArrayList<>();
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        
        double currentValue = initialCapital;
        Random random = new Random();
        
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (date.getDayOfWeek().getValue() <= 5) { // Weekdays only
                // Simulate daily returns
                double dailyReturn = random.nextGaussian() * 0.02; // 2% daily volatility
                currentValue *= (1 + dailyReturn);
                
                Map<String, Object> point = new HashMap<>();
                point.put("date", date.format(DateTimeFormatter.ISO_LOCAL_DATE));
                point.put("value", Math.round(currentValue * 100.0) / 100.0);
                curve.add(point);
            }
        }
        
        return curve;
    }
    
    private BacktestResponse convertToResponse(BacktestResult result) {
        Map<String, Object> results = new HashMap<>();
        results.put("total_return", result.getTotalReturn());
        results.put("sharpe_ratio", String.format("%.2f", result.getSharpeRatio()));
        results.put("max_drawdown", result.getMaxDrawdown());
        results.put("win_rate", result.getWinRate());
        results.put("total_trades", result.getTotalTrades());
        results.put("profitable_trades", result.getProfitableTrades());
        results.put("final_value", result.getFinalValue());
        
        List<Map<String, Object>> equityCurve = new ArrayList<>();
        try {
            if (result.getEquityCurveJson() != null) {
                equityCurve = objectMapper.readValue(result.getEquityCurveJson(), List.class);
            }
        } catch (Exception e) {
            // Return empty curve if parsing fails
        }
        
        return new BacktestResponse(result.getId(), results, equityCurve);
    }
}