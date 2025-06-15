package com.quantcrux.marketdata.service;

import com.quantcrux.marketdata.dto.MarketDataPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MarketDataService {
    
    private final Random random = new Random();
    
    public List<MarketDataPoint> getMarketData(String symbol, int days) {
        List<MarketDataPoint> data = new ArrayList<>();
        double price = getInitialPrice(symbol);
        LocalDate startDate = LocalDate.now().minusDays(days);
        
        for (int i = 0; i < days; i++) {
            LocalDate date = startDate.plusDays(i);
            
            // Generate realistic OHLCV data
            double volatility = 0.02;
            double drift = 0.0002;
            double randomFactor = (random.nextGaussian()) * volatility;
            
            price = price * (1 + drift + randomFactor);
            
            double open = price * (1 + (random.nextGaussian() - 0.5) * 0.01);
            double high = price * (1 + random.nextDouble() * 0.02);
            double low = price * (1 - random.nextDouble() * 0.02);
            double close = price;
            long volume = (long) (random.nextDouble() * 1000000 + 100000);
            
            data.add(new MarketDataPoint(date, open, high, low, close, volume));
        }
        
        return data;
    }
    
    private double getInitialPrice(String symbol) {
        return switch (symbol.toUpperCase()) {
            case "SPY" -> 400.0;
            case "AAPL" -> 150.0;
            case "GOOGL" -> 2500.0;
            case "MSFT" -> 300.0;
            case "EUR/USD" -> 1.10;
            case "GBP/USD" -> 1.25;
            case "USD/JPY" -> 110.0;
            default -> 100.0;
        };
    }
}