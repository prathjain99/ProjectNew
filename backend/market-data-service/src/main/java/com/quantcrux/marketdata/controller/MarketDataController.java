package com.quantcrux.marketdata.controller;

import com.quantcrux.marketdata.dto.MarketDataPoint;
import com.quantcrux.marketdata.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market-data")
@CrossOrigin(origins = "http://localhost:5173")
public class MarketDataController {
    
    @Autowired
    private MarketDataService marketDataService;
    
    @GetMapping("/{symbol}")
    public ResponseEntity<List<MarketDataPoint>> getMarketData(
            @PathVariable String symbol,
            @RequestParam(defaultValue = "252") int days) {
        
        List<MarketDataPoint> data = marketDataService.getMarketData(symbol, days);
        return ResponseEntity.ok(data);
    }
}