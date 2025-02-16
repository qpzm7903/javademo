package com.qpzm7903.day3;

import org.junit.jupiter.api.BeforeEach;

public abstract class BaseStockQuoteTest {
    protected StockQuoteGenerator generator;
    protected StockQuoteProcessor processor;

    @BeforeEach
    void setUp() {
        generator = new StockQuoteGenerator();
        processor = new StockQuoteProcessor();
    }
} 