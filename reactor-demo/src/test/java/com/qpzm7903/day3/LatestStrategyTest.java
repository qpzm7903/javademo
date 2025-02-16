package com.qpzm7903.day3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.qpzm7903.day3.model.StockQuote;

import reactor.core.publisher.Flux;

public class LatestStrategyTest extends BaseStockQuoteTest {

    @Test
    void testLatestStrategy() throws InterruptedException {
        processor.resetCounters();
        CountDownLatch latch = new CountDownLatch(1);

        Flux<StockQuote> quotes = generator.generateQuotes()
                .take(1000);

        processor.processWithLatest(quotes)
                .doOnComplete(latch::countDown)
                .subscribe();

        latch.await(10500, TimeUnit.MILLISECONDS);

        System.out.println("Latest Strategy Results:");
        System.out.println("Processed: " + processor.getProcessedCount());
        System.out.println("Dropped: " + processor.getDroppedCount());

        assertTrue(processor.getProcessedCount() >= 9,
                "Latest策略应该至少处理9个数据");
        assertTrue(processor.getProcessedCount() < 1000,
                "Latest策略不应处理所有数据");
    }
} 