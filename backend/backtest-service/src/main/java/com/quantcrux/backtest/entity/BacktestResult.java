package com.quantcrux.backtest.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "backtest_results")
public class BacktestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "strategy_id", nullable = false)
    private String strategyId;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(name = "symbol", nullable = false)
    private String symbol;
    
    @Column(name = "start_date")
    private String startDate;
    
    @Column(name = "end_date")
    private String endDate;
    
    @Column(name = "initial_capital")
    private Double initialCapital;
    
    @Column(name = "final_value")
    private Double finalValue;
    
    @Column(name = "total_return")
    private Double totalReturn;
    
    @Column(name = "sharpe_ratio")
    private Double sharpeRatio;
    
    @Column(name = "max_drawdown")
    private Double maxDrawdown;
    
    @Column(name = "win_rate")
    private Double winRate;
    
    @Column(name = "total_trades")
    private Integer totalTrades;
    
    @Column(name = "profitable_trades")
    private Integer profitableTrades;
    
    @Column(columnDefinition = "TEXT")
    private String equityCurveJson;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public BacktestResult() {}
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getStrategyId() { return strategyId; }
    public void setStrategyId(String strategyId) { this.strategyId = strategyId; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    
    public Double getInitialCapital() { return initialCapital; }
    public void setInitialCapital(Double initialCapital) { this.initialCapital = initialCapital; }
    
    public Double getFinalValue() { return finalValue; }
    public void setFinalValue(Double finalValue) { this.finalValue = finalValue; }
    
    public Double getTotalReturn() { return totalReturn; }
    public void setTotalReturn(Double totalReturn) { this.totalReturn = totalReturn; }
    
    public Double getSharpeRatio() { return sharpeRatio; }
    public void setSharpeRatio(Double sharpeRatio) { this.sharpeRatio = sharpeRatio; }
    
    public Double getMaxDrawdown() { return maxDrawdown; }
    public void setMaxDrawdown(Double maxDrawdown) { this.maxDrawdown = maxDrawdown; }
    
    public Double getWinRate() { return winRate; }
    public void setWinRate(Double winRate) { this.winRate = winRate; }
    
    public Integer getTotalTrades() { return totalTrades; }
    public void setTotalTrades(Integer totalTrades) { this.totalTrades = totalTrades; }
    
    public Integer getProfitableTrades() { return profitableTrades; }
    public void setProfitableTrades(Integer profitableTrades) { this.profitableTrades = profitableTrades; }
    
    public String getEquityCurveJson() { return equityCurveJson; }
    public void setEquityCurveJson(String equityCurveJson) { this.equityCurveJson = equityCurveJson; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}