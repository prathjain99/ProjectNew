package com.quantcrux.pricing.dto;

import java.math.BigDecimal;
import java.util.Map;

public class PricingResponse {
    private BigDecimal price;
    private Map<String, BigDecimal> greeks;
    private BigDecimal confidenceInterval;
    private Integer numSimulations;
    private String pricingMethod;
    
    // Constructors
    public PricingResponse() {}
    
    // Getters and Setters
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public Map<String, BigDecimal> getGreeks() { return greeks; }
    public void setGreeks(Map<String, BigDecimal> greeks) { this.greeks = greeks; }
    
    public BigDecimal getConfidenceInterval() { return confidenceInterval; }
    public void setConfidenceInterval(BigDecimal confidenceInterval) { this.confidenceInterval = confidenceInterval; }
    
    public Integer getNumSimulations() { return numSimulations; }
    public void setNumSimulations(Integer numSimulations) { this.numSimulations = numSimulations; }
    
    public String getPricingMethod() { return pricingMethod; }
    public void setPricingMethod(String pricingMethod) { this.pricingMethod = pricingMethod; }
}