package com.qpzm7903.day3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.qpzm7903.day3.model.StockQuote;

import reactor.core.publisher.Flux;

public class BufferStrategyTest extends BaseStockQuoteTest {

    @Test
    void testBufferStrategy() throws InterruptedException {
        processor.resetCounters();
        CountDownLatch latch = new CountDownLatch(1);

        Flux<StockQuote> quotes = generator.generateQuotes()
                .take(1000);

        processor.processWithBuffer(quotes)
                .doOnComplete(latch::countDown)
                .subscribe();

        latch.await(10, TimeUnit.SECONDS);

        System.out.println("Buffer Strategy Results:");
        System.out.println("Processed: " + processor.getProcessedCount());
        System.out.println("Dropped: " + processor.getDroppedCount());

        assertTrue(processor.getProcessedCount() > 0, "应该有被处理的数据");
        assertTrue(processor.getDroppedCount() > 0, "缓冲区满后应该有被丢弃的数据");
        assertTrue(processor.getProcessedCount() + processor.getDroppedCount() == 1000,
                "处理和丢弃的总数应该等于输入数据量");
    }
} 