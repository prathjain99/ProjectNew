package com.quantcrux.strategy.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "strategies")
public class Strategy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @Column(name = "created_by", nullable = false)
    private String createdBy;
    
    @ElementCollection
    @CollectionTable(name = "strategy_assets", joinColumns = @JoinColumn(name = "strategy_id"))
    @Column(name = "asset")
    private List<String> assetList;
    
    @Column(columnDefinition = "TEXT")
    private String indicatorsJson;
    
    @Column(columnDefinition = "TEXT")
    private String rulesJson;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public Strategy() {}
    
    public Strategy(String name, String description, String createdBy, List<String> assetList, 
                   String indicatorsJson, String rulesJson) {
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.assetList = assetList;
        this.indicatorsJson = indicatorsJson;
        this.rulesJson = rulesJson;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    
    public List<String> getAssetList() { return assetList; }
    public void setAssetList(List<String> assetList) { this.assetList = assetList; }
    
    public String getIndicatorsJson() { return indicatorsJson; }
    public void setIndicatorsJson(String indicatorsJson) { this.indicatorsJson = indicatorsJson; }
    
    public String getRulesJson() { return rulesJson; }
    public void setRulesJson(String rulesJson) { this.rulesJson = rulesJson; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}