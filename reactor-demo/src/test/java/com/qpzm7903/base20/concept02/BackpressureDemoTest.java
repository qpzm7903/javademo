package com.qpzm7903.base20.concept02;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * 背压策略测试
 */
public class BackpressureDemoTest {
    
    private BackpressureDemo demo = new BackpressureDemo();
    
    /**
     * 测试BUFFER策略
     * 验证：所有数据都被处理，但可能需要更多时间
     */
    @Test
    void testBufferStrategy() {
        AtomicInteger processed = new AtomicInteger();
        Flux<String> logs = generateLogs(100);
        
        StepVerifier.create(
                demo.processLogsWithBuffer(logs)
                    .doOnNext(log -> processed.incrementAndGet())
            )
            .expectSubscription()
            .thenAwait(Duration.ofSeconds(12))
            .expectComplete()
            .verify();
        
        assertTrue(processed.get() == 100, "所有日志都应该被处理");
    }
    
    /**
     * 测试DROP策略
     * 验证：部分数据被丢弃，但系统继续运行
     */
    @Test
    void testDropStrategy() {
        AtomicInteger processed = new AtomicInteger();
        Flux<String> logs = generateLogs(100);
        
        StepVerifier.create(
                demo.processLogsWithDrop(logs)
                    .doOnNext(log -> processed.incrementAndGet())
            )
            .expectSubscription()
            .thenAwait(Duration.ofSeconds(12))
            .expectComplete()
            .verify();
        
        assertTrue(processed.get() < 100, "部分日志应该被丢弃");
    }
    
    /**
     * 测试LATEST策略
     * 验证：只处理最新的数据
     */
    @Test
    void testLatestStrategy() {
        AtomicInteger processed = new AtomicInteger();
        Flux<String> logs = generateLogs(100);
        
        StepVerifier.create(
                demo.processLogsWithLatest(logs)
                    .doOnNext(log -> processed.incrementAndGet())
            )
            .expectSubscription()
            .thenAwait(Duration.ofSeconds(12))
            .expectComplete()
            .verify();
        
        assertTrue(processed.get() < 100, "应该只处理部分最新的日志");
    }
    
    /**
     * 测试ERROR策略
     * 验证：出现背压时抛出异常
     */
    @Test
    void testErrorStrategy() {
        Flux<String> logs = generateLogs(100);
        
        StepVerifier.create(demo.processLogsWithError(logs))
            .expectSubscription()
            .expectError()
            .verify();
    }
    
    /**
     * 生成测试日志流
     */
    private Flux<String> generateLogs(int count) {
        return Flux.range(1, count)
            .map(i -> "Log message " + i)
            .delayElements(Duration.ofMillis(50));  // 每50ms生成一条日志
    }
} 