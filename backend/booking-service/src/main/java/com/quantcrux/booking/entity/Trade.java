package com.quantcrux.booking.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "trades")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(name = "product_id", nullable = false)
    private String productId;
    
    @Column(name = "trade_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TradeType tradeType;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TradeStatus status = TradeStatus.PENDING;
    
    @Column(name = "notional", precision = 19, scale = 2)
    private BigDecimal notional;
    
    @Column(name = "entry_price", precision = 19, scale = 4)
    private BigDecimal entryPrice;
    
    @Column(name = "current_price", precision = 19, scale = 4)
    private BigDecimal currentPrice;
    
    @Column(name = "pnl", precision = 19, scale = 2)
    private BigDecimal pnl = BigDecimal.ZERO;
    
    @Column(name = "trade_date")
    private LocalDateTime tradeDate;
    
    @Column(name = "settlement_date")
    private LocalDateTime settlementDate;
    
    @Column(name = "maturity_date")
    private LocalDateTime maturityDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (tradeDate == null) {
            tradeDate = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Trade() {}
    
    public Trade(String userId, String productId, TradeType tradeType, BigDecimal notional, BigDecimal entryPrice) {
        this.userId = userId;
        this.productId = productId;
        this.tradeType = tradeType;
        this.notional = notional;
        this.entryPrice = entryPrice;
        this.currentPrice = entryPrice;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    
    public TradeType getTradeType() { return tradeType; }
    public void setTradeType(TradeType tradeType) { this.tradeType = tradeType; }
    
    public TradeStatus getStatus() { return status; }
    public void setStatus(TradeStatus status) { this.status = status; }
    
    public BigDecimal getNotional() { return notional; }
    public void setNotional(BigDecimal notional) { this.notional = notional; }
    
    public BigDecimal getEntryPrice() { return entryPrice; }
    public void setEntryPrice(BigDecimal entryPrice) { this.entryPrice = entryPrice; }
    
    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }
    
    public BigDecimal getPnl() { return pnl; }
    public void setPnl(BigDecimal pnl) { this.pnl = pnl; }
    
    public LocalDateTime getTradeDate() { return tradeDate; }
    public void setTradeDate(LocalDateTime tradeDate) { this.tradeDate = tradeDate; }
    
    public LocalDateTime getSettlementDate() { return settlementDate; }
    public void setSettlementDate(LocalDateTime settlementDate) { this.settlementDate = settlementDate; }
    
    public LocalDateTime getMaturityDate() { return maturityDate; }
    public void setMaturityDate(LocalDateTime maturityDate) { this.maturityDate = maturityDate; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}