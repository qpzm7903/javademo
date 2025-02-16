package com.qpzm7903.day3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.qpzm7903.day3.model.StockQuote;

import reactor.core.publisher.Flux;

public class DropStrategyTest extends BaseStockQuoteTest {

    @Test
    void testDropStrategy() throws InterruptedException {
        processor.resetCounters();
        generator.resetCounter();
        CountDownLatch latch = new CountDownLatch(1);

        Flux<StockQuote> quotes = generator.generateQuotes()
                .take(1000);

        processor.processWithDrop(quotes)
                .doOnComplete(latch::countDown)
                .subscribe();

        latch.await(10500, TimeUnit.MILLISECONDS);

        System.out.println("Drop Strategy Results:");
        System.out.println("Generated: " + generator.getGeneratedCount());
        System.out.println("Processed: " + processor.getProcessedCount());
        System.out.println("Dropped: " + processor.getDroppedCount());
        int totalHandled = processor.getProcessedCount() + processor.getDroppedCount();

        assertTrue(processor.getDroppedCount() > processor.getProcessedCount(),
                "Drop策略下丢弃的数据应该多于处理的数据");
        assertTrue(processor.getProcessedCount() >= 9 && processor.getProcessedCount() <= 11,
                "处理数量应该在9-11之间（考虑10秒处理时间）");
        assertTrue(totalHandled == generator.getGeneratedCount(),
                "处理和丢弃的总数应该等于生成的数量");
    }
} 