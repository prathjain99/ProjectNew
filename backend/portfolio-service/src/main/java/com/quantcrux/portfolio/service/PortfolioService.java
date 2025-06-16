package com.quantcrux.portfolio.service;

import com.quantcrux.portfolio.dto.PortfolioSummary;
import com.quantcrux.portfolio.entity.Position;
import com.quantcrux.portfolio.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PortfolioService {
    
    @Autowired
    private PositionRepository positionRepository;
    
    public PortfolioSummary getPortfolioSummary(String userId) {
        List<Position> positions = positionRepository.findByUserIdAndIsActiveTrue(userId);
        
        if (positions.isEmpty()) {
            // Create mock position for demo
            Position mockPosition = createMockPosition(userId);
            positions = List.of(mockPosition);
        }
        
        // Calculate summary
        double totalValue = positions.stream().mapToDouble(p -> p.getCurrentValue() != null ? p.getCurrentValue() : 0.0).sum();
        double totalInvestment = positions.stream().mapToDouble(p -> p.getTotalInvestment() != null ? p.getTotalInvestment() : 0.0).sum();
        double totalPnl = totalValue - totalInvestment;
        double pnlPercentage = totalInvestment > 0 ? (totalPnl / totalInvestment) * 100 : 0.0;
        
        PortfolioSummary.Summary summary = new PortfolioSummary.Summary(
                totalValue, totalInvestment, totalPnl, pnlPercentage, positions.size()
        );
        
        // Convert positions to DTOs
        List<PortfolioSummary.PositionDto> positionDtos = positions.stream()
                .map(this::convertToPositionDto)
                .toList();
        
        return new PortfolioSummary(summary, positionDtos);
    }
    
    public void createPosition(String userId, String productId, Double quantity, Double entryPrice) {
        Position position = new Position(userId, productId, quantity, entryPrice);
        positionRepository.save(position);
    }
    
    private Position createMockPosition(String userId) {
        Position position = new Position();
        position.setUserId(userId);
        position.setProductId("mock-product-1");
        position.setQuantity(1000.0);
        position.setEntryPrice(100.0);
        position.setCurrentPrice(125.0);
        position.setTotalInvestment(100000.0);
        position.setCurrentValue(125000.0);
        position.setUnrealizedPnl(25000.0);
        return position;
    }
    
    private PortfolioSummary.PositionDto convertToPositionDto(Position position) {
        PortfolioSummary.PositionDto dto = new PortfolioSummary.PositionDto();
        dto.setId(position.getId());
        dto.setProductId(position.getProductId());
        dto.setQuantity(position.getQuantity());
        dto.setEntryPrice(position.getEntryPrice());
        dto.setCurrentValue(position.getCurrentValue());
        dto.setTotalInvestment(position.getTotalInvestment());
        dto.setUnrealizedPnl(position.getUnrealizedPnl());
        
        // Mock product data
        PortfolioSummary.ProductDto product = new PortfolioSummary.ProductDto(
                position.getProductId(),
                "EUR/USD Digital Option",
                "digital_option",
                "EUR/USD"
        );
        dto.setProduct(product);
        
        return dto;
    }
}