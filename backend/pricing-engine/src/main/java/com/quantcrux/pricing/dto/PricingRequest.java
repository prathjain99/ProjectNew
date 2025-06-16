package com.quantcrux.pricing.dto;

public class PricingRequest {
    private String productType;
    private Double spotPrice;
    private Double strike;
    private Double barrier;
    private Double coupon;
    private Double volatility;
    private Double riskFreeRate;
    private Double timeToMaturity;
    private Integer numSimulations;
    
    // Constructors
    public PricingRequest() {}
    
    // Getters and Setters
    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }
    
    public Double getSpotPrice() { return spotPrice; }
    public void setSpotPrice(Double spotPrice) { this.spotPrice = spotPrice; }
    
    public Double getStrike() { return strike; }
    public void setStrike(Double strike) { this.strike = strike; }
    
    public Double getBarrier() { return barrier; }
    public void setBarrier(Double barrier) { this.barrier = barrier; }
    
    public Double getCoupon() { return coupon; }
    public void setCoupon(Double coupon) { this.coupon = coupon; }
    
    public Double getVolatility() { return volatility; }
    public void setVolatility(Double volatility) { this.volatility = volatility; }
    
    public Double getRiskFreeRate() { return riskFreeRate; }
    public void setRiskFreeRate(Double riskFreeRate) { this.riskFreeRate = riskFreeRate; }
    
    public Double getTimeToMaturity() { return timeToMaturity; }
    public void setTimeToMaturity(Double timeToMaturity) { this.timeToMaturity = timeToMaturity; }
    
    public Integer getNumSimulations() { return numSimulations; }
    public void setNumSimulations(Integer numSimulations) { this.numSimulations = numSimulations; }
}