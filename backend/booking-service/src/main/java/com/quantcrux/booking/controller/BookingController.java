package com.quantcrux.booking.controller;

import com.quantcrux.booking.dto.TradeRequest;
import com.quantcrux.booking.dto.TradeResponse;
import com.quantcrux.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    @PostMapping("/book")
    public ResponseEntity<TradeResponse> bookTrade(
            @RequestBody TradeRequest request,
            @RequestHeader("X-User-Id") String userId) {
        try {
            TradeResponse response = bookingService.bookTrade(request, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Trade booking failed: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<TradeResponse>> getUserTrades(@RequestHeader("X-User-Id") String userId) {
        List<TradeResponse> trades = bookingService.getUserTrades(userId);
        return ResponseEntity.ok(trades);
    }
    
    @GetMapping("/{tradeId}")
    public ResponseEntity<TradeResponse> getTrade(@PathVariable String tradeId) {
        TradeResponse trade = bookingService.getTradeById(tradeId);
        return ResponseEntity.ok(trade);
    }
    
    @PutMapping("/{tradeId}/status")
    public ResponseEntity<TradeResponse> updateTradeStatus(
            @PathVariable String tradeId,
            @RequestParam String status) {
        try {
            TradeResponse response = bookingService.updateTradeStatus(tradeId, status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Booking service is running");
    }
}