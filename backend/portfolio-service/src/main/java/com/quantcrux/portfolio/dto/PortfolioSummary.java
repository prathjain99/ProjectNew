package com.quantcrux.portfolio.dto;

import java.util.List;

public class PortfolioSummary {
    private Summary summary;
    private List<PositionDto> positions;
    
    // Constructors
    public PortfolioSummary() {}
    
    public PortfolioSummary(Summary summary, List<PositionDto> positions) {
        this.summary = summary;
        this.positions = positions;
    }
    
    // Getters and Setters
    public Summary getSummary() { return summary; }
    public void setSummary(Summary summary) { this.summary = summary; }
    
    public List<PositionDto> getPositions() { return positions; }
    public void setPositions(List<PositionDto> positions) { this.positions = positions; }
    
    public static class Summary {
        private Double totalValue;
        private Double totalInvestment;
        private Double totalPnl;
        private Double pnlPercentage;
        private Integer positionCount;
        
        // Constructors
        public Summary() {}
        
        public Summary(Double totalValue, Double totalInvestment, Double totalPnl, Double pnlPercentage, Integer positionCount) {
            this.totalValue = totalValue;
            this.totalInvestment = totalInvestment;
            this.totalPnl = totalPnl;
            this.pnlPercentage = pnlPercentage;
            this.positionCount = positionCount;
        }
        
        // Getters and Setters
        public Double getTotalValue() { return totalValue; }
        public void setTotalValue(Double totalValue) { this.totalValue = totalValue; }
        
        public Double getTotalInvestment() { return totalInvestment; }
        public void setTotalInvestment(Double totalInvestment) { this.totalInvestment = totalInvestment; }
        
        public Double getTotalPnl() { return totalPnl; }
        public void setTotalPnl(Double totalPnl) { this.totalPnl = totalPnl; }
        
        public Double getPnlPercentage() { return pnlPercentage; }
        public void setPnlPercentage(Double pnlPercentage) { this.pnlPercentage = pnlPercentage; }
        
        public Integer getPositionCount() { return positionCount; }
        public void setPositionCount(Integer positionCount) { this.positionCount = positionCount; }
    }
    
    public static class PositionDto {
        private String id;
        private String productId;
        private ProductDto product;
        private Double quantity;
        private Double entryPrice;
        private Double currentValue;
        private Double totalInvestment;
        private Double unrealizedPnl;
        
        // Constructors
        public PositionDto() {}
        
        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        
        public ProductDto getProduct() { return product; }
        public void setProduct(ProductDto product) { this.product = product; }
        
        public Double getQuantity() { return quantity; }
        public void setQuantity(Double quantity) { this.quantity = quantity; }
        
        public Double getEntryPrice() { return entryPrice; }
        public void setEntryPrice(Double entryPrice) { this.entryPrice = entryPrice; }
        
        public Double getCurrentValue() { return currentValue; }
        public void setCurrentValue(Double currentValue) { this.currentValue = currentValue; }
        
        public Double getTotalInvestment() { return totalInvestment; }
        public void setTotalInvestment(Double totalInvestment) { this.totalInvestment = totalInvestment; }
        
        public Double getUnrealizedPnl() { return unrealizedPnl; }
        public void setUnrealizedPnl(Double unrealizedPnl) { this.unrealizedPnl = unrealizedPnl; }
    }
    
    public static class ProductDto {
        private String id;
        private String name;
        private String type;
        private String underlyingAsset;
        
        // Constructors
        public ProductDto() {}
        
        public ProductDto(String id, String name, String type, String underlyingAsset) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.underlyingAsset = underlyingAsset;
        }
        
        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getUnderlyingAsset() { return underlyingAsset; }
        public void setUnderlyingAsset(String underlyingAsset) { this.underlyingAsset = underlyingAsset; }
    }
}