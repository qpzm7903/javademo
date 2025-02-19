package com.qpzm7903.base20.concept14;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

/**
 * 背压策略与流量控制测试
 */
public class BackpressureFlowControlDemoTest {
    
    private BackpressureFlowControlDemo demo;
    
    @BeforeEach
    void setUp() {
        demo = new BackpressureFlowControlDemo();
        demo.resetCounters();
    }
    
    /**
     * 测试基础背压<br>
     * 验证request机制
     */
    @Test
    void testBasicBackpressure() {
        StepVerifier.create(demo.basicBackpressure().take(10))
                .expectNextCount(10)
                .verifyComplete();
        
        assertEquals(10, demo.getProducedCount());
        assertEquals(10, demo.getConsumedCount());
    }
    
    /**
     * 测试缓冲策略<br>
     * 验证批处理行为
     */
    @Test
    void testBufferStrategy() {
        StepVerifier.create(demo.bufferStrategy())
                .expectNextCount(10)  // 10个批次，每批10个元素
                .verifyComplete();
        
        assertEquals(100, demo.getProducedCount());
        assertEquals(100, demo.getConsumedCount());
    }
    
    /**
     * 测试丢弃策略<br>
     * 验证元素丢弃行为
     */
    @Test
    void testDropStrategy() {
        CountDownLatch latch = new CountDownLatch(1);
        
        demo.dropStrategy()
                .subscribeOn(Schedulers.parallel())
                .doOnComplete(latch::countDown)
                .subscribe();
        
        try {
            assertTrue(latch.await(5, TimeUnit.SECONDS));
            Thread.sleep(100); // Give time for final counts to settle
            assertTrue(demo.getDroppedCount() > 0);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 测试最新值策略<br>
     * 验证最新值保留行为
     */
    @Test
    void testLatestStrategy() {
        StepVerifier.create(demo.latestStrategy().take(5))
                .expectNextCount(5)
                .verifyComplete();
        
        assertEquals(5, demo.getConsumedCount());
    }
    
    /**
     * 测试错误策略<br>
     * 验证过载错误处理
     */
    @Test
    void testErrorStrategy() {
        StepVerifier.create(demo.errorStrategy().take(5))
                .expectNextCount(5)
                .verifyComplete();
        
        assertEquals(5, demo.getConsumedCount());
    }
    
    /**
     * 测试自定义背压<br>
     * 验证自定义背压处理
     */
    @Test
    void testCustomBackpressure() {
        StepVerifier.create(demo.customBackpressure().take(10))
                .expectNextCount(10)
                .verifyComplete();
        
        assertEquals(10, demo.getProducedCount());
        assertEquals(10, demo.getConsumedCount());
    }
    
    /**
     * 测试限速策略<br>
     * 验证流速限制
     */
    @Test
    void testRateLimitStrategy() {
        StepVerifier.create(demo.rateLimitStrategy().take(10))
                .expectNextCount(10)
                .verifyComplete();
        
        assertEquals(10, demo.getProducedCount());
        assertEquals(10, demo.getConsumedCount());
    }
    
    /**
     * 测试采样策略<br>
     * 验证采样行为
     */
    @Test
    void testSamplingStrategy() {
        StepVerifier.withVirtualTime(() -> demo.samplingStrategy().take(5))
                .expectSubscription()
                .thenAwait(Duration.ofMillis(50))
                .expectNext(3L)  // Values 0,1,2,3 emitted, 3 is last
                .thenAwait(Duration.ofMillis(50))
                .expectNext(8L)  // Values 4,5,6,7,8 emitted, 8 is last
                .thenAwait(Duration.ofMillis(50))
                .expectNext(13L) // Values 9,10,11,12,13 emitted, 13 is last
                .thenAwait(Duration.ofMillis(50))
                .expectNext(18L) // Values 14,15,16,17,18 emitted, 18 is last
                .thenAwait(Duration.ofMillis(50))
                .expectNext(23L) // Values 19,20,21,22,23 emitted, 23 is last
                .verifyComplete();
        
        assertTrue(demo.getProducedCount() >= 5);
        assertEquals(5, demo.getConsumedCount());
    }
    
    @Test
    void test_duration() throws InterruptedException {
        Flux.interval(Duration.ofMillis(10))
                .doOnNext(System.out::println)
                .take(5)
                .subscribe();
        Thread.sleep(50);
    }
} 