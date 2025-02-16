package com.qpzm7903.day3;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qpzm7903.day3.model.StockQuote;

import reactor.core.publisher.Flux;

public class StockQuoteProcessor {
    private static final Logger logger = LoggerFactory.getLogger(StockQuoteProcessor.class);
    private final AtomicInteger processedCount = new AtomicInteger(0);
    private final AtomicInteger droppedCount = new AtomicInteger(0);

    /**
     * 使用BUFFER策略处理股票报价
     * 当消费者处理速度跟不上时，将数据缓存起来
     */
    public Flux<StockQuote> processWithBuffer(Flux<StockQuote> quotes) {
        return quotes
                .onBackpressureBuffer(10, // 减小缓冲区大小为10
                        dropped -> {
                            droppedCount.incrementAndGet();
                            logger.warn("Buffer full, dropped quote: {}", dropped);
                        })
                .doOnNext(quote -> {
                    simulateSlowProcessing();
                    processedCount.incrementAndGet();
                    logger.info("Processed quote with BUFFER strategy: {}", quote);
                });
    }

    /**
     * 使用DROP策略处理股票报价
     * 当消费者处理速度跟不上时，丢弃多余的数据
     */
    public Flux<StockQuote> processWithDrop(Flux<StockQuote> quotes) {
        return quotes
                .onBackpressureDrop(dropped -> {
                    droppedCount.incrementAndGet();
                    logger.warn("Dropped quote due to backpressure: {}", dropped);
                })
                .doOnNext(quote -> {
                    simulateSlowProcessing();
                    processedCount.incrementAndGet();
                    logger.info("Processed quote with DROP strategy: {}", quote);
                });
    }

    /**
     * 使用LATEST策略处理股票报价
     * 当消费者处理速度跟不上时，只保留最新的数据
     */
    public Flux<StockQuote> processWithLatest(Flux<StockQuote> quotes) {
        return quotes
                .onBackpressureLatest()
                .doOnNext(quote -> {
                    simulateSlowProcessing();
                    processedCount.incrementAndGet();
                    logger.info("Processed quote with LATEST strategy: {}", quote);
                });
    }

    /**
     * 模拟耗时处理
     * 增加处理时间到500-600ms，加大生产和消费的速率差异
     */
    private void simulateSlowProcessing() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // 获取处理统计信息
    public int getProcessedCount() {
        return processedCount.get();
    }

    public int getDroppedCount() {
        return droppedCount.get();
    }

    // 重置计数器
    public void resetCounters() {
        processedCount.set(0);
        droppedCount.set(0);
    }
} 