package com.quantcrux.product.dto;

public class ProductRequest {
    private String name;
    private String type;
    private String underlyingAsset;
    private Double strike;
    private Double barrier;
    private Double coupon;
    private Double notional;
    private Integer maturityMonths;
    private String issuer;
    private String currency;
    
    // Constructors
    public ProductRequest() {}
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getUnderlyingAsset() { return underlyingAsset; }
    public void setUnderlyingAsset(String underlyingAsset) { this.underlyingAsset = underlyingAsset; }
    
    public Double getStrike() { return strike; }
    public void setStrike(Double strike) { this.strike = strike; }
    
    public Double getBarrier() { return barrier; }
    public void setBarrier(Double barrier) { this.barrier = barrier; }
    
    public Double getCoupon() { return coupon; }
    public void setCoupon(Double coupon) { this.coupon = coupon; }
    
    public Double getNotional() { return notional; }
    public void setNotional(Double notional) { this.notional = notional; }
    
    public Integer getMaturityMonths() { return maturityMonths; }
    public void setMaturityMonths(Integer maturityMonths) { this.maturityMonths = maturityMonths; }
    
    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}