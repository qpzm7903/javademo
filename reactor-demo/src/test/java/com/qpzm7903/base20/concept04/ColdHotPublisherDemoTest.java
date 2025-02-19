package com.qpzm7903.base20.concept04;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 冷热数据源测试
 */
public class ColdHotPublisherDemoTest {
    
    private ColdHotPublisherDemo demo = new ColdHotPublisherDemo();
    
    /**
     * 测试冷数据源：每个订阅者都获取完整数据
     */
    @Test
    void testColdPublisher() {
        Flux<String> coldSource = demo.coldDatabaseQuery();
        
        // 第一个订阅者
        List<String> subscriber1Results = new ArrayList<>();
        coldSource.subscribe(subscriber1Results::add);
        assertEquals(3, subscriber1Results.size());
        
        // 第二个订阅者
        List<String> subscriber2Results = new ArrayList<>();
        coldSource.subscribe(subscriber2Results::add);
        assertEquals(3, subscriber2Results.size());
        
        // 验证两个订阅者获取的是相同的数据
        assertEquals(subscriber1Results, subscriber2Results);
    }
    
    /**
     * 测试热数据源：订阅者只能获取订阅后的数据
     */
    @Test
    void testHotPublisher() throws InterruptedException {
        ConnectableFlux<StockPrice> hotSource = demo.hotStockTicker();
        
        List<StockPrice> subscriber1Prices = new CopyOnWriteArrayList<>();
        List<StockPrice> subscriber2Prices = new CopyOnWriteArrayList<>();
        
        // 第一个订阅者立即订阅
        hotSource.subscribe(subscriber1Prices::add);
        hotSource.connect();
        
        Thread.sleep(2000); // 等待2秒
        
        // 第二个订阅者延迟订阅
        hotSource.subscribe(subscriber2Prices::add);
        
        Thread.sleep(2000); // 再等待2秒
        
        // 验证第一个订阅者收到更多数据
        assertTrue(subscriber1Prices.size() > subscriber2Prices.size());
    }
    
    /**
     * 测试Sinks多播热数据源
     */
    @Test
    void testHotSinks() {
        Sinks.Many<String> sink = demo.createHotEventSource();
        List<String> subscriber1Events = new ArrayList<>();
        List<String> subscriber2Events = new ArrayList<>();
        
        // 两个订阅者
        sink.asFlux().subscribe(subscriber1Events::add);
        sink.asFlux().subscribe(subscriber2Events::add);
        
        // 发送事件
        sink.tryEmitNext("Event 1");
        sink.tryEmitNext("Event 2");
        
        assertEquals(2, subscriber1Events.size());
        assertEquals(2, subscriber2Events.size());
        assertEquals(subscriber1Events, subscriber2Events);
    }
    
    /**
     * 测试冷转热数据源
     */
    @Test
    void testColdToHot() {
        Flux<String> coldSource = Flux.just("Data 1", "Data 2", "Data 3");
        ConnectableFlux<String> hotSource = demo.coldToHot(coldSource);
        
        List<String> subscriber1Data = new ArrayList<>();
        List<String> subscriber2Data = new ArrayList<>();
        
        // 两个订阅者同时订阅
        hotSource.subscribe(subscriber1Data::add);
        hotSource.subscribe(subscriber2Data::add);
        
        // 验证两个订阅者收到相同的数据
        assertEquals(subscriber1Data, subscriber2Data);
    }
} 