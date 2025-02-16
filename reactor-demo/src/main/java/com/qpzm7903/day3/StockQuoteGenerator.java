package com.qpzm7903.day3;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.qpzm7903.day3.model.StockQuote;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class StockQuoteGenerator {
    private final Random random = new Random();
    private final String[] symbols = {"AAPL", "GOOGL", "MSFT", "AMZN"};
    private final double[] basePrice = {180.0, 140.0, 370.0, 130.0};
    private final AtomicInteger generatedCount = new AtomicInteger(0);  // 添加计数器

    /**
     * 生成高频股票报价流
     * 每10ms生成一个新的报价，模拟高频交易场景
     */
    public Flux<StockQuote> generateQuotes() {
        return Flux.interval(Duration.ofMillis(10))
                .publishOn(Schedulers.newSingle("generator"))  // 使用单独的固定线程
                .map(i -> {
                    generatedCount.incrementAndGet();  // 增加计数
                    return generateRandomQuote();
                });
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

    // 添加获取生成数量的方法
    public int getGeneratedCount() {
        return generatedCount.get();
    }

    // 添加重置计数器的方法
    public void resetCounter() {
        generatedCount.set(0);
    }
} 