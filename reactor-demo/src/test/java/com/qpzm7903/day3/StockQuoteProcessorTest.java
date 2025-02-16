package com.qpzm7903.day3;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.qpzm7903.day3.model.StockQuote;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class StockQuoteProcessorTest {

    private StockQuoteGenerator generator;
    private StockQuoteProcessor processor;

    @BeforeEach
    void setUp() {
        generator = new StockQuoteGenerator();
        processor = new StockQuoteProcessor();
    }

    @Test
    void testBufferStrategy() throws InterruptedException {
        processor.resetCounters();
        CountDownLatch latch = new CountDownLatch(1);

        // 使用generator生成报价
        Flux<StockQuote> quotes = generator.generateQuotes()
                .take(1000);  // 限制数量为1000个报价

        processor.processWithBuffer(quotes)
                .doOnComplete(latch::countDown)
                .subscribe();

        // 等待足够长的时间让处理完成
        latch.await(10, TimeUnit.SECONDS);

        System.out.println("Buffer Strategy Results:");
        System.out.println("Processed: " + processor.getProcessedCount());
        System.out.println("Dropped: " + processor.getDroppedCount());

        // Buffer策略应该处理所有数据直到缓冲区满
        assertTrue(processor.getProcessedCount() > 0, "应该有被处理的数据");
        assertTrue(processor.getDroppedCount() > 0, "缓冲区满后应该有被丢弃的数据");
        assertTrue(processor.getProcessedCount() + processor.getDroppedCount() == 1000, 
                "处理和丢弃的总数应该等于输入数据量");
    }

    @Test
    void testDropStrategy() throws InterruptedException {
        processor.resetCounters();
        CountDownLatch latch = new CountDownLatch(1);

        Flux<StockQuote> quotes = generator.generateQuotes()
                .take(1000);

        processor.processWithDrop(quotes)
                .doOnComplete(latch::countDown)
                .subscribe();

        latch.await(10, TimeUnit.SECONDS);

        System.out.println("Drop Strategy Results:");
        System.out.println("Processed: " + processor.getProcessedCount());
        System.out.println("Dropped: " + processor.getDroppedCount());

        assertTrue(processor.getDroppedCount() > processor.getProcessedCount(), 
                "Drop策略下丢弃的数据应该多于处理的数据");
        assertTrue(processor.getProcessedCount() + processor.getDroppedCount() == 1000,
                "处理和丢弃的总数应该等于输入数据量");
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

        latch.await(10, TimeUnit.SECONDS);

        System.out.println("Latest Strategy Results:");
        System.out.println("Processed: " + processor.getProcessedCount());
        System.out.println("Dropped: " + processor.getDroppedCount());

        assertTrue(processor.getProcessedCount() >= 15, 
                "Latest策略应该至少处理15个数据");
        assertTrue(processor.getProcessedCount() < 1000,
                "Latest策略不应处理所有数据");
    }

    /**
     * 添加一个可视化测试，展示不同策略的处理模式
     */
    @Test
    void visualizeBackpressureStrategies() {
        // Buffer策略
        StepVerifier.withVirtualTime(() -> {
            Flux<StockQuote> quotes = generator.generateQuotes()
                    .take(50);
            return processor.processWithBuffer(quotes);
        })
        .thenAwait(Duration.ofSeconds(30))
        .expectComplete()
        .verify();

        System.out.println("\nVisualization of different backpressure strategies:");
        System.out.println("Buffer: [1][2][3][4][5]...until buffer full, then drop");
        System.out.println("Drop  : [1]...drop...[5]...drop...[9]");
        System.out.println("Latest: [1]...skip...[5]...skip...[latest]");
    }
} 