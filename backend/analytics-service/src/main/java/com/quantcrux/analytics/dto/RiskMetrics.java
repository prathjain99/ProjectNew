package com.quantcrux.analytics.dto;

public class RiskMetrics {
    private Double var95;
    private Double var99;
    private Double beta;
    private Double sharpeRatio;
    private Double sortinoRatio;
    private Double maxDrawdown;
    private Double volatility;
    private Double correlationSpy;
    
    // Constructors
    public RiskMetrics() {}
    
    public RiskMetrics(Double var95, Double var99, Double beta, Double sharpeRatio, 
                      Double sortinoRatio, Double maxDrawdown, Double volatility, Double correlationSpy) {
        this.var95 = var95;
        this.var99 = var99;
        this.beta = beta;
        this.sharpeRatio = sharpeRatio;
        this.sortinoRatio = sortinoRatio;
        this.maxDrawdown = maxDrawdown;
        this.volatility = volatility;
        this.correlationSpy = correlationSpy;
    }
    
    // Getters and Setters
    public Double getVar95() { return var95; }
    public void setVar95(Double var95) { this.var95 = var95; }
    
    public Double getVar99() { return var99; }
    public void setVar99(Double var99) { this.var99 = var99; }
    
    public Double getBeta() { return beta; }
    public void setBeta(Double beta) { this.beta = beta; }
    
    public Double getSharpeRatio() { return sharpeRatio; }
    public void setSharpeRatio(Double sharpeRatio) { this.sharpeRatio = sharpeRatio; }
    
    public Double getSortinoRatio() { return sortinoRatio; }
    public void setSortinoRatio(Double sortinoRatio) { this.sortinoRatio = sortinoRatio; }
    
    public Double getMaxDrawdown() { return maxDrawdown; }
    public void setMaxDrawdown(Double maxDrawdown) { this.maxDrawdown = maxDrawdown; }
    
    public Double getVolatility() { return volatility; }
    public void setVolatility(Double volatility) { this.volatility = volatility; }
    
    public Double getCorrelationSpy() { return correlationSpy; }
    public void setCorrelationSpy(Double correlationSpy) { this.correlationSpy = correlationSpy; }
}