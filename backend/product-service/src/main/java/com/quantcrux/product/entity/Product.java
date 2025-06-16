package com.quantcrux.product.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "product_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType type;
    
    @Column(name = "underlying_asset", nullable = false)
    private String underlyingAsset;
    
    @Column(name = "strike_price")
    private Double strike;
    
    @Column(name = "barrier_level")
    private Double barrier;
    
    @Column(name = "coupon_rate")
    private Double coupon;
    
    @Column(name = "notional_amount")
    private Double notional;
    
    @Column(name = "maturity_months")
    private Integer maturityMonths;
    
    @Column(name = "issuer")
    private String issuer;
    
    @Column(name = "currency")
    private String currency;
    
    @Column(name = "created_by", nullable = false)
    private String createdBy;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public Product() {}
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public ProductType getType() { return type; }
    public void setType(ProductType type) { this.type = type; }
    
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
    
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}

enum ProductType {
    DIGITAL_OPTION,
    BARRIER_OPTION,
    DUAL_CURRENCY,
    AUTOCALLABLE,
    REVERSE_CONVERTIBLE
}