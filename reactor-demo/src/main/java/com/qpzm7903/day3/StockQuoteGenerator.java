package com.qpzm7903.day3;

import java.time.Duration;
import java.util.Random;

import com.qpzm7903.day3.model.StockQuote;

import reactor.core.publisher.Flux;

public class StockQuoteGenerator {
    private final Random random = new Random();
    private final String[] symbols = {"AAPL", "GOOGL", "MSFT", "AMZN"};
    private final double[] basePrice = {180.0, 140.0, 370.0, 130.0};

    /**
     * 生成高频股票报价流
     * 每1ms生成一个新的报价，模拟高频交易场景
     */
    public Flux<StockQuote> generateQuotes() {
        return Flux.interval(Duration.ofMillis(10))
                .map(i -> generateRandomQuote()); 
                // 移除 boundedElastic 调度器，让生产者直接在调用线程中生成数据：
                // .publishOn(Schedulers.boundedElastic()); // 使用弹性线程池
    }

    /**
     * 生成一个随机的股票报价
     * 在基准价格的基础上添加随机波动
     */
    private StockQuote generateRandomQuote() {
        int index = random.nextInt(symbols.length);
        double priceChange = (random.nextDouble() - 0.5) * 2.0; // 随机价格波动，范围[-1,1]
        double newPrice = basePrice[index] + priceChange;
        return new StockQuote(symbols[index], newPrice);
    }
} 