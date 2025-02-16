package com.qpzm7903.day3;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qpzm7903.day3.model.StockQuote;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class StockQuoteBackpressureTest {
    private static final Logger logger = LoggerFactory.getLogger(StockQuoteBackpressureTest.class);
    
    protected StockQuoteGenerator generator;
    protected StockQuoteProcessor processor;
    
    @BeforeEach
    void setUp() {
        generator = new StockQuoteGenerator();
        processor = new StockQuoteProcessor();
    }
    
    @Test
    void testBufferStrategy() throws InterruptedException {
        processor.resetCounters();
        CountDownLatch latch = new CountDownLatch(1);
        
        logger.info("开始Buffer策略测试");
        Flux<StockQuote> quotes = generator.generateQuotes()
                .take(100)
                .doOnComplete(() -> logger.info("数据生成完成"));
        
        processor.processWithBuffer(quotes)
                .doOnComplete(() -> {
                    logger.info("处理完成");
                    latch.countDown();
                })
                .subscribe(
                    quote -> {},
                    error -> {
                        logger.error("处理出错: {}", error.getMessage());
                        latch.countDown();
                    }
                );
        
        boolean completed = latch.await(12000, TimeUnit.MILLISECONDS);  // 增加等待时间
        
        logger.info("测试完成: {}", completed ? "正常完成" : "超时");
        logger.info("处理数量: {}", processor.getProcessedCount());
        logger.info("丢弃数量: {}", processor.getDroppedCount());
        
        int total = processor.getProcessedCount() + processor.getDroppedCount();
        assertTrue(processor.getProcessedCount() > 0, "应该有被处理的数据");
        assertTrue(processor.getDroppedCount() > 0, "缓冲区满后应该有被丢弃的数据");
        assertTrue(total == 100,
                String.format("处理和丢弃的总数应该等于输入数据量100，实际是%d", total));
    }
    
    @Test
    void testDropStrategy() throws InterruptedException {
        processor.resetCounters();
        generator.resetCounter();
        CountDownLatch latch = new CountDownLatch(1);
        
        Flux<StockQuote> quotes = generator.generateQuotes()
                .take(200);
        
        processor.processWithDrop(quotes)
                .doOnComplete(latch::countDown)
                .subscribe();
        
        latch.await(2000, TimeUnit.MILLISECONDS);
        
        System.out.println("Drop Strategy Results:");
        System.out.println("Generated: " + generator.getGeneratedCount());
        System.out.println("Processed: " + processor.getProcessedCount());
        System.out.println("Dropped: " + processor.getDroppedCount());
        int totalHandled = processor.getProcessedCount() + processor.getDroppedCount();
        
        assertTrue(processor.getDroppedCount() > processor.getProcessedCount(),
                "Drop策略下丢弃的数据应该多于处理的数据");
        assertTrue(processor.getProcessedCount() >= 9 && processor.getProcessedCount() <= 11,
                "处理数量应该在9-11之间（考虑10秒处理时间）");
        assertTrue(totalHandled == generator.getGeneratedCount(), "处理和丢弃的总数应该等于生成的数量");
    }
    
    @Test
    void testLatestStrategy() throws InterruptedException {
        processor.resetCounters();
        CountDownLatch latch = new CountDownLatch(1);
        
        Flux<StockQuote> quotes = generator.generateQuotes()
                .take(1000);
        
        processor.processWithLatest(quotes)
                .doOnComplete(latch::countDown)
                .subscribe();
        
        latch.await(1050, TimeUnit.MILLISECONDS);
        
        System.out.println("Latest Strategy Results:");
        System.out.println("Processed: " + processor.getProcessedCount());
        System.out.println("Dropped: " + processor.getDroppedCount());
        
        assertTrue(processor.getProcessedCount() >= 9, "Latest策略应该至少处理9个数据");
        assertTrue(processor.getProcessedCount() < 1000, "Latest策略不应处理所有数据");
    }
    
    @Test
    void visualizeBackpressureStrategies() {
        StepVerifier.withVirtualTime(() -> processor.processWithBuffer(generator.generateQuotes()
                        .take(100)))
                .thenAwait(Duration.ofSeconds(1))
                .expectComplete()
                .verify();
        
        System.out.println("\nVisualization of different backpressure strategies:");
        System.out.println("Buffer: [1][2][3][4][5]...until buffer full, then drop");
        System.out.println("Drop  : [1]...drop...[5]...drop...[9]");
        System.out.println("Latest: [1]...skip...[5]...skip...[latest]");
    }
}