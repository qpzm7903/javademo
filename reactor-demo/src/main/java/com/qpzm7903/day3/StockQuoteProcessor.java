package com.qpzm7903.day3;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qpzm7903.day3.model.StockQuote;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * 股票报价处理器
 * 实现了三种不同的背压策略来处理高频股票报价数据
 * 
 * 背压处理流程：
 * 1. 数据生成：StockQuoteGenerator 每10ms生成一个报价（生产速率：100个/秒）
 * 2. 数据处理：每个报价处理需要1000ms（消费速率：1个/秒）
 * 3. 在10秒运行时间内：
 *    - 产生：1000个报价
 *    - 处理：约10个报价
 *    - 丢弃：约990个报价
 */
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
     * 
     * 背压实现说明：
     * 1. onBackpressureDrop 放在流的最前面，直接处理上游数据
     * 2. publishOn 在 onBackpressureDrop 之后，确保消费者在单独线程运行
     * 3. doOnNext 最后处理未被丢弃的数据
     * 
     * 操作符顺序的重要性：
     * - 如果 publishOn 在 onBackpressureDrop 之前，publishOn 会创建自己的缓冲区
     * - 这会导致背压信号被 publishOn 处理，onBackpressureDrop 失效
     * 
     * 正确的顺序确保：
     * 1. 背压控制在最上游进行
     * 2. 过载时立即丢弃数据
     * 3. 处理线程不会影响背压策略
     * 4. 准确统计处理和丢弃的数量
     */
    public Flux<StockQuote> processWithDrop(Flux<StockQuote> quotes) {
        return quotes
                .onBackpressureDrop(dropped -> {
                    droppedCount.incrementAndGet();
                    logger.warn("Dropped quote due to backpressure: {}, current dropped count: {}", 
                        dropped, droppedCount.get());
                })
                .publishOn(Schedulers.boundedElastic(), 1) // 设置较小的请求大小
                .doOnNext(quote -> {
                    logger.info("Processing quote: {}, current processed count: {}", 
                        quote, processedCount.get());
                    simulateSlowProcessing();
                    processedCount.incrementAndGet();
                });
    }

    /**
     * 使用LATEST策略处理股票报价
     * 当消费者处理速度跟不上时，只保留最新的数据
     */
    public Flux<StockQuote> processWithLatest(Flux<StockQuote> quotes) {
        return quotes
                .onBackpressureLatest()
                .publishOn(Schedulers.boundedElastic(), 1) // 设置较小的请求大小
                .doOnNext(quote -> {
                    simulateSlowProcessing();
                    processedCount.incrementAndGet();
                    logger.info("Processed quote with LATEST strategy: {}", quote);
                });
    }

    /**
     * 模拟耗时处理
     * 增加处理时间到1000ms，加大生产和消费的速率差异
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