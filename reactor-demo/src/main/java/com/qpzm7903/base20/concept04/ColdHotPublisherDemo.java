package com.qpzm7903.base20.concept04;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * 冷热数据源(Cold vs Hot Publisher)演示
 * 
 * 典型场景1：数据库查询 vs 消息队列
 * - 冷：数据库查询，每个订阅者都获取完整数据
 * - 热：消息队列，订阅者只能获取订阅后的数据
 * 
 * 典型场景2：文件读取 vs 实时股票行情
 * - 冷：文件读取，每个订阅者都从头开始读
 * - 热：股票行情，订阅者只能获取当前价格
 */
public class ColdHotPublisherDemo {
    
    /**
     * 冷数据源示例：数据库查询
     * 特点：
     * 1. 每个订阅者都获取完整数据
     * 2. 数据从头开始发送
     * 3. 订阅时才开始生成数据
     */
    public Flux<String> coldDatabaseQuery() {
        return Flux.defer(() -> {
            System.out.println("执行数据库查询...");
            return Flux.fromIterable(queryDatabase());
        });
    }
    
    /**
     * 热数据源示例：股票行情
     * 特点：
     * 1. 所有订阅者共享数据流
     * 2. 只能获取订阅后的数据
     * 3. 数据源独立于订阅者运行
     */
    public ConnectableFlux<StockPrice> hotStockTicker() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(i -> new StockPrice("AAPL", 100 + i))
            .publish();
    }
    
    /**
     * 使用Sinks创建多播热数据源
     * 适用场景：需要手动控制数据发送
     */
    public Sinks.Many<String> createHotEventSource() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }
    
    /**
     * 冷数据源转热数据源
     * 使用场景：需要共享昂贵的数据处理结果
     */
    public ConnectableFlux<String> coldToHot(Flux<String> coldSource) {
        return coldSource.publish();
    }
    
    /**
     * 模拟数据库查询
     */
    private List<String> queryDatabase() {
        List<String> results = new ArrayList<>();
        results.add("Record 1");
        results.add("Record 2");
        results.add("Record 3");
        return results;
    }
}

class StockPrice {
    private final String symbol;
    private final double price;
    
    public StockPrice(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
    
    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }
    
    @Override
    public String toString() {
        return String.format("%s: $%.2f", symbol, price);
    }
} 