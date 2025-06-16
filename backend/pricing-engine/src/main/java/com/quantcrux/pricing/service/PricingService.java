package com.quantcrux.pricing.service;

import com.quantcrux.pricing.dto.PricingRequest;
import com.quantcrux.pricing.dto.PricingResponse;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class PricingService {
    
    private final Random random = new Random();
    private final NormalDistribution normalDist = new NormalDistribution();
    
    public PricingResponse calculatePrice(PricingRequest request) {
        PricingResponse response = new PricingResponse();
        
        switch (request.getProductType().toLowerCase()) {
            case "digital_option":
                response = priceDigitalOption(request);
                break;
            case "barrier_option":
                response = priceBarrierOption(request);
                break;
            case "autocallable":
                response = priceAutocallable(request);
                break;
            default:
                response = priceGenericProduct(request);
        }
        
        return response;
    }
    
    public PricingResponse monteCarloPrice(PricingRequest request) {
        int numSimulations = request.getNumSimulations() != null ? request.getNumSimulations() : 100000;
        double timeToMaturity = request.getTimeToMaturity() != null ? request.getTimeToMaturity() : 1.0;
        double volatility = request.getVolatility() != null ? request.getVolatility() : 0.2;
        double riskFreeRate = request.getRiskFreeRate() != null ? request.getRiskFreeRate() : 0.05;
        double spot = request.getSpotPrice() != null ? request.getSpotPrice() : 100.0;
        
        double[] payoffs = new double[numSimulations];
        double totalPayoff = 0.0;
        
        for (int i = 0; i < numSimulations; i++) {
            double finalPrice = simulatePrice(spot, riskFreeRate, volatility, timeToMaturity);
            double payoff = calculatePayoff(finalPrice, request);
            payoffs[i] = payoff;
            totalPayoff += payoff;
        }
        
        double averagePayoff = totalPayoff / numSimulations;
        double discountedPrice = averagePayoff * Math.exp(-riskFreeRate * timeToMaturity);
        
        // Calculate confidence intervals
        double variance = 0.0;
        for (double payoff : payoffs) {
            variance += Math.pow(payoff - averagePayoff, 2);
        }
        variance /= (numSimulations - 1);
        double standardError = Math.sqrt(variance / numSimulations);
        double confidenceInterval = 1.96 * standardError * Math.exp(-riskFreeRate * timeToMaturity);
        
        PricingResponse response = new PricingResponse();
        response.setPrice(BigDecimal.valueOf(discountedPrice).setScale(4, RoundingMode.HALF_UP));
        response.setConfidenceInterval(BigDecimal.valueOf(confidenceInterval).setScale(4, RoundingMode.HALF_UP));
        response.setNumSimulations(numSimulations);
        
        // Calculate Greeks using finite differences
        Map<String, BigDecimal> greeks = calculateGreeks(request, discountedPrice);
        response.setGreeks(greeks);
        
        return response;
    }
    
    private double simulatePrice(double spot, double rate, double vol, double time) {
        double drift = (rate - 0.5 * vol * vol) * time;
        double diffusion = vol * Math.sqrt(time) * normalDist.sample();
        return spot * Math.exp(drift + diffusion);
    }
    
    private double calculatePayoff(double finalPrice, PricingRequest request) {
        double strike = request.getStrike() != null ? request.getStrike() : 100.0;
        double barrier = request.getBarrier() != null ? request.getBarrier() : 80.0;
        double coupon = request.getCoupon() != null ? request.getCoupon() : 0.1;
        
        switch (request.getProductType().toLowerCase()) {
            case "digital_option":
                return finalPrice > strike ? coupon * 100 : 0.0;
            case "barrier_option":
                return (finalPrice > barrier && finalPrice > strike) ? coupon * 100 : 0.0;
            case "autocallable":
                if (finalPrice >= strike * 1.1) {
                    return coupon * 100; // Early redemption
                } else if (finalPrice >= barrier) {
                    return coupon * 100; // Normal coupon
                } else {
                    return Math.max(0, finalPrice - strike); // Capital protection
                }
            default:
                return Math.max(0, finalPrice - strike);
        }
    }
    
    private PricingResponse priceDigitalOption(PricingRequest request) {
        // Simplified Black-Scholes for digital option
        double spot = request.getSpotPrice() != null ? request.getSpotPrice() : 100.0;
        double strike = request.getStrike() != null ? request.getStrike() : 100.0;
        double vol = request.getVolatility() != null ? request.getVolatility() : 0.2;
        double rate = request.getRiskFreeRate() != null ? request.getRiskFreeRate() : 0.05;
        double time = request.getTimeToMaturity() != null ? request.getTimeToMaturity() : 1.0;
        
        double d2 = (Math.log(spot / strike) + (rate - 0.5 * vol * vol) * time) / (vol * Math.sqrt(time));
        double price = Math.exp(-rate * time) * normalDist.cumulativeProbability(d2);
        
        PricingResponse response = new PricingResponse();
        response.setPrice(BigDecimal.valueOf(price * 100).setScale(4, RoundingMode.HALF_UP));
        response.setGreeks(calculateGreeks(request, price * 100));
        
        return response;
    }
    
    private PricingResponse priceBarrierOption(PricingRequest request) {
        // Simplified barrier option pricing
        double basePrice = priceDigitalOption(request).getPrice().doubleValue();
        double barrier = request.getBarrier() != null ? request.getBarrier() : 80.0;
        double spot = request.getSpotPrice() != null ? request.getSpotPrice() : 100.0;
        
        // Apply barrier adjustment
        double barrierAdjustment = Math.exp(-Math.pow((spot - barrier) / spot, 2));
        double adjustedPrice = basePrice * barrierAdjustment;
        
        PricingResponse response = new PricingResponse();
        response.setPrice(BigDecimal.valueOf(adjustedPrice).setScale(4, RoundingMode.HALF_UP));
        response.setGreeks(calculateGreeks(request, adjustedPrice));
        
        return response;
    }
    
    private PricingResponse priceAutocallable(PricingRequest request) {
        // Simplified autocallable pricing
        double basePrice = priceDigitalOption(request).getPrice().doubleValue();
        double autocallLevel = request.getStrike() != null ? request.getStrike() * 1.1 : 110.0;
        double spot = request.getSpotPrice() != null ? request.getSpotPrice() : 100.0;
        
        // Probability of autocall
        double autocallProb = spot > autocallLevel ? 0.8 : 0.3;
        double adjustedPrice = basePrice * (1 + autocallProb * 0.2);
        
        PricingResponse response = new PricingResponse();
        response.setPrice(BigDecimal.valueOf(adjustedPrice).setScale(4, RoundingMode.HALF_UP));
        response.setGreeks(calculateGreeks(request, adjustedPrice));
        
        return response;
    }
    
    private PricingResponse priceGenericProduct(PricingRequest request) {
        // Generic product pricing
        double spot = request.getSpotPrice() != null ? request.getSpotPrice() : 100.0;
        double strike = request.getStrike() != null ? request.getStrike() : 100.0;
        double vol = request.getVolatility() != null ? request.getVolatility() : 0.2;
        double time = request.getTimeToMaturity() != null ? request.getTimeToMaturity() : 1.0;
        
        double price = spot * (1 + random.nextGaussian() * vol * Math.sqrt(time) * 0.1);
        
        PricingResponse response = new PricingResponse();
        response.setPrice(BigDecimal.valueOf(price).setScale(4, RoundingMode.HALF_UP));
        response.setGreeks(calculateGreeks(request, price));
        
        return response;
    }
    
    private Map<String, BigDecimal> calculateGreeks(PricingRequest request, double basePrice) {
        Map<String, BigDecimal> greeks = new HashMap<>();
        
        double spot = request.getSpotPrice() != null ? request.getSpotPrice() : 100.0;
        double vol = request.getVolatility() != null ? request.getVolatility() : 0.2;
        double time = request.getTimeToMaturity() != null ? request.getTimeToMaturity() : 1.0;
        
        // Delta (price sensitivity to underlying)
        double deltaShift = 0.01;
        PricingRequest deltaRequest = copyRequest(request);
        deltaRequest.setSpotPrice(spot * (1 + deltaShift));
        double priceUp = calculatePrice(deltaRequest).getPrice().doubleValue();
        double delta = (priceUp - basePrice) / (spot * deltaShift);
        
        // Gamma (delta sensitivity to underlying)
        deltaRequest.setSpotPrice(spot * (1 - deltaShift));
        double priceDown = calculatePrice(deltaRequest).getPrice().doubleValue();
        double gamma = (priceUp - 2 * basePrice + priceDown) / Math.pow(spot * deltaShift, 2);
        
        // Vega (price sensitivity to volatility)
        double vegaShift = 0.01;
        PricingRequest vegaRequest = copyRequest(request);
        vegaRequest.setVolatility(vol + vegaShift);
        double vegaPrice = calculatePrice(vegaRequest).getPrice().doubleValue();
        double vega = (vegaPrice - basePrice) / vegaShift;
        
        // Theta (price sensitivity to time)
        double thetaShift = 1.0 / 365.0; // 1 day
        PricingRequest thetaRequest = copyRequest(request);
        thetaRequest.setTimeToMaturity(time - thetaShift);
        double thetaPrice = calculatePrice(thetaRequest).getPrice().doubleValue();
        double theta = (thetaPrice - basePrice) / thetaShift;
        
        greeks.put("delta", BigDecimal.valueOf(delta).setScale(6, RoundingMode.HALF_UP));
        greeks.put("gamma", BigDecimal.valueOf(gamma).setScale(6, RoundingMode.HALF_UP));
        greeks.put("vega", BigDecimal.valueOf(vega).setScale(6, RoundingMode.HALF_UP));
        greeks.put("theta", BigDecimal.valueOf(theta).setScale(6, RoundingMode.HALF_UP));
        
        return greeks;
    }
    
    private PricingRequest copyRequest(PricingRequest original) {
        PricingRequest copy = new PricingRequest();
        copy.setProductType(original.getProductType());
        copy.setSpotPrice(original.getSpotPrice());
        copy.setStrike(original.getStrike());
        copy.setBarrier(original.getBarrier());
        copy.setCoupon(original.getCoupon());
        copy.setVolatility(original.getVolatility());
        copy.setRiskFreeRate(original.getRiskFreeRate());
        copy.setTimeToMaturity(original.getTimeToMaturity());
        copy.setNumSimulations(original.getNumSimulations());
        return copy;
    }
}