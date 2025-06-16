package com.quantcrux.lifecycle.service;

import com.quantcrux.lifecycle.dto.LifecycleEventResponse;
import com.quantcrux.lifecycle.entity.EventStatus;
import com.quantcrux.lifecycle.entity.EventType;
import com.quantcrux.lifecycle.entity.LifecycleEvent;
import com.quantcrux.lifecycle.repository.LifecycleEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LifecycleService {
    
    @Autowired
    private LifecycleEventRepository lifecycleEventRepository;
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    private final Random random = new Random();
    
    @KafkaListener(topics = "trade-events")
    public void handleTradeEvent(Object tradeEvent) {
        // Process trade events and create lifecycle events
        System.out.println("Received trade event: " + tradeEvent);
        // Implementation would parse the event and create appropriate lifecycle events
    }
    
    @Scheduled(cron = "0 0 9 * * MON-FRI") // Daily at 9 AM on weekdays
    public void processScheduledFixings() {
        System.out.println("Processing scheduled fixings...");
        
        // Mock processing - in real implementation, would fetch market data and process fixings
        List<LifecycleEvent> pendingFixings = lifecycleEventRepository.findByEventTypeAndStatus(
            EventType.FIXING, EventStatus.PENDING
        );
        
        for (LifecycleEvent event : pendingFixings) {
            // Simulate market data fetch
            BigDecimal currentPrice = BigDecimal.valueOf(100 + random.nextGaussian() * 10);
            event.setUnderlyingPrice(currentPrice);
            event.setStatus(EventStatus.PROCESSED);
            event.setProcessedAt(LocalDateTime.now());
            
            lifecycleEventRepository.save(event);
            
            // Check for barrier breaches or autocalls
            checkEventTriggers(event);
        }
    }
    
    public void checkBarrierBreaches() {
        System.out.println("Checking barrier breaches...");
        
        // Mock barrier checking logic
        List<LifecycleEvent> activeEvents = lifecycleEventRepository.findByStatus(EventStatus.PENDING);
        
        for (LifecycleEvent event : activeEvents) {
            if (event.getBarrierLevel() != null && event.getUnderlyingPrice() != null) {
                if (event.getUnderlyingPrice().compareTo(event.getBarrierLevel()) <= 0) {
                    // Barrier breached
                    LifecycleEvent breachEvent = new LifecycleEvent(
                        event.getTradeId(),
                        event.getProductId(),
                        EventType.BARRIER_BREACH,
                        LocalDateTime.now()
                    );
                    breachEvent.setUnderlyingPrice(event.getUnderlyingPrice());
                    breachEvent.setBarrierLevel(event.getBarrierLevel());
                    breachEvent.setStatus(EventStatus.PROCESSED);
                    breachEvent.setProcessedAt(LocalDateTime.now());
                    
                    lifecycleEventRepository.save(breachEvent);
                    
                    // Publish barrier breach event
                    publishLifecycleEvent("BARRIER_BREACH", breachEvent);
                }
            }
        }
    }
    
    private void checkEventTriggers(LifecycleEvent event) {
        // Check for autocall conditions
        if (event.getUnderlyingPrice() != null && event.getBarrierLevel() != null) {
            if (event.getUnderlyingPrice().compareTo(event.getBarrierLevel().multiply(BigDecimal.valueOf(1.1))) >= 0) {
                // Autocall triggered
                LifecycleEvent autocallEvent = new LifecycleEvent(
                    event.getTradeId(),
                    event.getProductId(),
                    EventType.AUTOCALL,
                    LocalDateTime.now()
                );
                autocallEvent.setUnderlyingPrice(event.getUnderlyingPrice());
                autocallEvent.setCouponAmount(BigDecimal.valueOf(10000)); // Mock coupon
                autocallEvent.setStatus(EventStatus.PROCESSED);
                autocallEvent.setProcessedAt(LocalDateTime.now());
                
                lifecycleEventRepository.save(autocallEvent);
                
                publishLifecycleEvent("AUTOCALL", autocallEvent);
            }
        }
    }
    
    public List<LifecycleEventResponse> getTradeEvents(String tradeId) {
        List<LifecycleEvent> events = lifecycleEventRepository.findByTradeIdOrderByEventDateDesc(tradeId);
        return events.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    private void publishLifecycleEvent(String eventType, LifecycleEvent event) {
        try {
            LifecycleEventMessage message = new LifecycleEventMessage(
                eventType, event.getTradeId(), event.getProductId(), 
                event.getEventType().toString(), event.getUnderlyingPrice()
            );
            kafkaTemplate.send("lifecycle-events", message);
        } catch (Exception e) {
            System.err.println("Failed to publish lifecycle event: " + e.getMessage());
        }
    }
    
    private LifecycleEventResponse convertToResponse(LifecycleEvent event) {
        LifecycleEventResponse response = new LifecycleEventResponse();
        response.setId(event.getId());
        response.setTradeId(event.getTradeId());
        response.setProductId(event.getProductId());
        response.setEventType(event.getEventType().toString());
        response.setEventDate(event.getEventDate());
        response.setUnderlyingPrice(event.getUnderlyingPrice());
        response.setBarrierLevel(event.getBarrierLevel());
        response.setCouponAmount(event.getCouponAmount());
        response.setStatus(event.getStatus().toString());
        response.setProcessedAt(event.getProcessedAt());
        response.setNotes(event.getNotes());
        return response;
    }
    
    // Inner class for Kafka messages
    public static class LifecycleEventMessage {
        private String eventType;
        private String tradeId;
        private String productId;
        private String lifecycleEventType;
        private BigDecimal underlyingPrice;
        private LocalDateTime timestamp;
        
        public LifecycleEventMessage(String eventType, String tradeId, String productId, 
                                   String lifecycleEventType, BigDecimal underlyingPrice) {
            this.eventType = eventType;
            this.tradeId = tradeId;
            this.productId = productId;
            this.lifecycleEventType = lifecycleEventType;
            this.underlyingPrice = underlyingPrice;
            this.timestamp = LocalDateTime.now();
        }
        
        // Getters and setters
        public String getEventType() { return eventType; }
        public void setEventType(String eventType) { this.eventType = eventType; }
        
        public String getTradeId() { return tradeId; }
        public void setTradeId(String tradeId) { this.tradeId = tradeId; }
        
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        
        public String getLifecycleEventType() { return lifecycleEventType; }
        public void setLifecycleEventType(String lifecycleEventType) { this.lifecycleEventType = lifecycleEventType; }
        
        public BigDecimal getUnderlyingPrice() { return underlyingPrice; }
        public void setUnderlyingPrice(BigDecimal underlyingPrice) { this.underlyingPrice = underlyingPrice; }
        
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }
}