package com.qpzm7903.base20.concept05;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 订阅触发机制测试
 */
public class SubscriptionTriggersDemoTest {
    
    private SubscriptionTriggersDemo demo = new SubscriptionTriggersDemo();
    
    /**
     * 测试基础订阅
     */
    @Test
    void testBasicSubscription() {
        List<Integer> results = new ArrayList<>();
        
        demo.basicSubscription()
            .subscribe(results::add);
        
        assertEquals(5, results.size());
        assertEquals(List.of(1, 2, 3, 4, 5), results);
    }
    
    /**
     * 测试延迟订阅
     */
    @Test
    void testDelayedSubscription() {
        VirtualTimeScheduler.getOrSet();
        
        StepVerifier.withVirtualTime(() -> demo.delayedSubscription())
            .expectSubscription()
            .expectNoEvent(Duration.ofSeconds(2))
            .expectNext(1, 2, 3, 4, 5)
            .verifyComplete();
    }
    
    /**
     * 测试条件订阅
     */
    @Test
    void testConditionalSubscription() {
        // 测试条件为true的情况
        StepVerifier.create(demo.conditionalSubscription(true))
            .expectNext("数据1", "数据2", "数据3")
            .verifyComplete();
        
        // 测试条件为false的情况
        StepVerifier.create(demo.conditionalSubscription(false))
            .verifyComplete();
    }
    
    /**
     * 测试延迟加载数据库
     */
    @Test
    void testLazyDatabaseQuery() {
        assertEquals(0, demo.getDatabaseCallCount());
        
        // 第一次查询
        StepVerifier.create(demo.lazyDatabaseQuery("1"))
            .expectNext("数据库结果: 1")
            .verifyComplete();
        
        assertEquals(1, demo.getDatabaseCallCount());
        
        // 第二次查询
        StepVerifier.create(demo.lazyDatabaseQuery("2"))
            .expectNext("数据库结果: 2")
            .verifyComplete();
        
        assertEquals(2, demo.getDatabaseCallCount());
    }
    
    /**
     * 测试缓存刷新
     */
    @Test
    void testCacheRefresh() {
        assertEquals(0, demo.getCacheRefreshCount());
        
        // 第一次订阅
        StepVerifier.create(demo.cacheRefresh())
            .expectNext("缓存数据1", "缓存数据2")
            .verifyComplete();
        
        assertEquals(1, demo.getCacheRefreshCount());
        
        // 第二次订阅
        StepVerifier.create(demo.cacheRefresh())
            .expectNext("缓存数据1", "缓存数据2")
            .verifyComplete();
        
        assertEquals(2, demo.getCacheRefreshCount());
    }
    
    /**
     * 测试重试订阅
     */
    @Test
    void testRetryingSubscription() {
        StepVerifier.create(demo.retryingSubscription())
            .expectNextCount(1)
            .verifyComplete();
    }
    
    /**
     * 测试并行订阅
     */
    @Test
    void testParallelSubscription() {
        List<String> results = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        
        demo.parallelSubscription()
            .doOnComplete(latch::countDown)
            .subscribe(results::add);
        
        try {
            latch.await(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertEquals(5, results.size());
        assertTrue(results.stream().allMatch(s -> s.startsWith("并行处理 #")));
    }
} 