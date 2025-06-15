package com.quantcrux.strategy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StrategyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StrategyServiceApplication.class, args);
    }
}