package com.quantcrux.strategy.dto;

import java.util.List;
import java.util.Map;

public class StrategyRequest {
    private String name;
    private String description;
    private List<String> assetList;
    private Map<String, Object> indicators;
    private Map<String, Object> rules;
    
    // Constructors
    public StrategyRequest() {}
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public List<String> getAssetList() { return assetList; }
    public void setAssetList(List<String> assetList) { this.assetList = assetList; }
    
    public Map<String, Object> getIndicators() { return indicators; }
    public void setIndicators(Map<String, Object> indicators) { this.indicators = indicators; }
    
    public Map<String, Object> getRules() { return rules; }
    public void setRules(Map<String, Object> rules) { this.rules = rules; }
}