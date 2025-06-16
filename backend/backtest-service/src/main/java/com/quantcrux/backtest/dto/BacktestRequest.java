package com.quantcrux.backtest.dto;

public class BacktestRequest {
    private String strategyId;
    private String symbol;
    private String startDate;
    private String endDate;
    private Double initialCapital;
    
    // Constructors
    public BacktestRequest() {}
    
    // Getters and Setters
    public String getStrategyId() { return strategyId; }
    public void setStrategyId(String strategyId) { this.strategyId = strategyId; }
    
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    
    public Double getInitialCapital() { return initialCapital; }
    public void setInitialCapital(Double initialCapital) { this.initialCapital = initialCapital; }
}