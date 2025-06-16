package com.quantcrux.booking.service;

import com.quantcrux.booking.dto.TradeRequest;
import com.quantcrux.booking.dto.TradeResponse;
import com.quantcrux.booking.entity.Trade;
import com.quantcrux.booking.entity.TradeStatus;
import com.quantcrux.booking.entity.TradeType;
import com.quantcrux.booking.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    
    @Autowired
    private TradeRepository tradeRepository;
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    public TradeResponse bookTrade(TradeRequest request, String userId) {
        Trade trade = new Trade(
            userId,
            request.getProductId(),
            TradeType.valueOf(request.getTradeType().toUpperCase()),
            request.getNotional(),
            request.getEntryPrice()
        );
        
        trade.setNotes(request.getNotes());
        trade.setStatus(TradeStatus.BOOKED);
        trade.setSettlementDate(LocalDateTime.now().plusDays(2)); // T+2 settlement
        
        Trade savedTrade = tradeRepository.save(trade);
        
        // Publish trade event to Kafka
        publishTradeEvent("TRADE_BOOKED", savedTrade);
        
        return convertToResponse(savedTrade);
    }
    
    public List<TradeResponse> getUserTrades(String userId) {
        List<Trade> trades = tradeRepository.findByUserIdOrderByTradeeDateDesc(userId);
        return trades.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public TradeResponse getTradeById(String tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("Trade not found"));
        return convertToResponse(trade);
    }
    
    public TradeResponse updateTradeStatus(String tradeId, String status) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("Trade not found"));
        
        TradeStatus oldStatus = trade.getStatus();
        trade.setStatus(TradeStatus.valueOf(status.toUpperCase()));
        
        Trade updatedTrade = tradeRepository.save(trade);
        
        // Publish status change event
        publishTradeEvent("TRADE_STATUS_CHANGED", updatedTrade);
        
        return convertToResponse(updatedTrade);
    }
    
    private void publishTradeEvent(String eventType, Trade trade) {
        try {
            TradeEvent event = new TradeEvent(eventType, trade.getId(), trade.getUserId(), 
                                            trade.getProductId(), trade.getStatus().toString());
            kafkaTemplate.send("trade-events", event);
        } catch (Exception e) {
            System.err.println("Failed to publish trade event: " + e.getMessage());
        }
    }
    
    private TradeResponse convertToResponse(Trade trade) {
        TradeResponse response = new TradeResponse();
        response.setId(trade.getId());
        response.setUserId(trade.getUserId());
        response.setProductId(trade.getProductId());
        response.setProductName("Mock Product Name"); // Would fetch from product service
        response.setTradeType(trade.getTradeType().toString());
        response.setStatus(trade.getStatus().toString());
        response.setNotional(trade.getNotional());
        response.setEntryPrice(trade.getEntryPrice());
        response.setCurrentPrice(trade.getCurrentPrice());
        response.setPnl(trade.getPnl());
        response.setTradeDate(trade.getTradeDate());
        response.setSettlementDate(trade.getSettlementDate());
        response.setMaturityDate(trade.getMaturityDate());
        response.setNotes(trade.getNotes());
        return response;
    }
    
    // Inner class for Kafka events
    public static class TradeEvent {
        private String eventType;
        private String tradeId;
        private String userId;
        private String productId;
        private String status;
        private LocalDateTime timestamp;
        
        public TradeEvent(String eventType, String tradeId, String userId, String productId, String status) {
            this.eventType = eventType;
            this.tradeId = tradeId;
            this.userId = userId;
            this.productId = productId;
            this.status = status;
            this.timestamp = LocalDateTime.now();
        }
        
        // Getters and setters
        public String getEventType() { return eventType; }
        public void setEventType(String eventType) { this.eventType = eventType; }
        
        public String getTradeId() { return tradeId; }
        public void setTradeId(String tradeId) { this.tradeId = tradeId; }
        
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }
}