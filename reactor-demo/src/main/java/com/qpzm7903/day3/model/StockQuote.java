package com.qpzm7903.day3.model;

import java.time.LocalDateTime;

public class StockQuote {
    private final String symbol;
    private final double price;
    private final LocalDateTime timestamp;

    public StockQuote(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = LocalDateTime.now();
    }

    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("StockQuote{symbol='%s', price=%.2f, timestamp=%s}",
                symbol, price, timestamp);
    }
} 