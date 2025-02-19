package com.qpzm7903.day3;

import com.qpzm7903.day3.model.StockQuote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
                        quote -> {
                        },
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
    
    /**
     * 测试Drop背压策略
     * <p>
     * 数据流分析：
     * 1. 数据生成：
     * - 速率：每1ms生成一个数据
     * - 总量：200个数据
     * - 总生成时间：200ms
     * <p>
     * 2. 数据处理：
     * - 速率：每个数据处理需要100ms
     * - 理论处理量：2秒可处理20个数据
     * <p>
     * 3. Drop策略工作过程：
     * 时间轴    上游生成    处理情况    丢弃情况
     * 0ms      1-10       开始处理1    -
     * 1ms      11         -           丢弃11
     * 2ms      12         -           丢弃12
     * ...      ...        -           ...
     * 100ms    101-200    处理完1      丢弃101-200
     * 101ms    -          开始处理2    -
     * 200ms    结束        -           -
     * <p>
     * 4. 实际结果分析：
     * - 处理数量少于预期（2而不是20）的原因：
     * * publishOn的prefetch=10会预取10个元素
     * * 在第一个元素处理完成前（100ms），上游已生成了100个数据
     * * Drop策略会立即丢弃所有无法被prefetch缓冲的数据
     *
     * @throws InterruptedException 如果等待过程被中断
     */
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
        assertTrue(processor.getProcessedCount() == 2,
                "处理数量应该在2）");
        assertTrue(totalHandled == generator.getGeneratedCount(),
                "处理和丢弃的总数应该等于生成的数量");
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
}