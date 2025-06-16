package com.quantcrux.lifecycle.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "lifecycle_events")
public class LifecycleEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "trade_id", nullable = false)
    private String tradeId;
    
    @Column(name = "product_id", nullable = false)
    private String productId;
    
    @Column(name = "event_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    
    @Column(name = "underlying_price", precision = 19, scale = 4)
    private BigDecimal underlyingPrice;
    
    @Column(name = "barrier_level", precision = 19, scale = 4)
    private BigDecimal barrierLevel;
    
    @Column(name = "coupon_amount", precision = 19, scale = 2)
    private BigDecimal couponAmount;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.PENDING;
    
    @Column(name = "processed_at")
    private LocalDateTime processedAt;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public LifecycleEvent() {}
    
    public LifecycleEvent(String tradeId, String productId, EventType eventType, LocalDateTime eventDate) {
        this.tradeId = tradeId;
        this.productId = productId;
        this.eventType = eventType;
        this.eventDate = eventDate;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTradeId() { return tradeId; }
    public void setTradeId(String tradeId) { this.tradeId = tradeId; }
    
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    
    public EventType getEventType() { return eventType; }
    public void setEventType(EventType eventType) { this.eventType = eventType; }
    
    public LocalDateTime getEventDate() { return eventDate; }
    public void setEventDate(LocalDateTime eventDate) { this.eventDate = eventDate; }
    
    public BigDecimal getUnderlyingPrice() { return underlyingPrice; }
    public void setUnderlyingPrice(BigDecimal underlyingPrice) { this.underlyingPrice = underlyingPrice; }
    
    public BigDecimal getBarrierLevel() { return barrierLevel; }
    public void setBarrierLevel(BigDecimal barrierLevel) { this.barrierLevel = barrierLevel; }
    
    public BigDecimal getCouponAmount() { return couponAmount; }
    public void setCouponAmount(BigDecimal couponAmount) { this.couponAmount = couponAmount; }
    
    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }
    
    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}

enum EventType {
    FIXING, COUPON_PAYMENT, BARRIER_BREACH, KNOCK_IN, KNOCK_OUT, AUTOCALL, MATURITY, EARLY_REDEMPTION
}

enum EventStatus {
    PENDING, PROCESSED, FAILED, CANCELLED
}