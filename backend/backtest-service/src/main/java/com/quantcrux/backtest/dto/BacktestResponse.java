package com.quantcrux.backtest.dto;

import java.util.List;
import java.util.Map;

public class BacktestResponse {
    private String id;
    private Map<String, Object> results;
    private List<Map<String, Object>> equityCurve;
    
    // Constructors
    public BacktestResponse() {}
    
    public BacktestResponse(String id, Map<String, Object> results, List<Map<String, Object>> equityCurve) {
        this.id = id;
        this.results = results;
        this.equityCurve = equityCurve;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Map<String, Object> getResults() { return results; }
    public void setResults(Map<String, Object> results) { this.results = results; }
    
    public List<Map<String, Object>> getEquityCurve() { return equityCurve; }
    public void setEquityCurve(List<Map<String, Object>> equityCurve) { this.equityCurve = equityCurve; }
}