package com.qpzm7903.base20.concept13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 测试与调试技巧测试类
 */
public class TestingDebugDemoTest {
    
    private TestingDebugDemo demo;
    
    @BeforeEach
    void setUp() {
        demo = new TestingDebugDemo();
        demo.resetCounter();
    }
    
    /**
     * 测试基础流操作<br>
     * 使用StepVerifier验证流程
     */
    @Test
    void testBasicOperations() {
        StepVerifier.create(demo.basicOperations("test"))
                .expectNext("Processed: TEST")
                .verifyComplete();
        
        assertEquals(2, demo.getOperationCount());
    }
    
    /**
     * 测试延时操作<br>
     * 使用虚拟时间调度器
     */
    @Test
    void testDelayedOperations() {
        StepVerifier.withVirtualTime(() -> demo.delayedOperations())
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1))
                .expectNext("Event 0")
                .expectNoEvent(Duration.ofSeconds(1))
                .expectNext("Event 1")
                .expectNoEvent(Duration.ofSeconds(1))
                .expectNext("Event 2")
                .verifyComplete();
        
        assertEquals(3, demo.getOperationCount());
    }
    
    /**
     * 测试错误处理<br>
     * 验证正常和错误场景
     */
    @Test
    void testErrorHandling() {
        // 测试成功场景
        StepVerifier.create(demo.errorHandling(false))
                .expectNext("Success")
                .verifyComplete();
        
        assertEquals(1, demo.getOperationCount());
        
        // 测试失败场景
        StepVerifier.create(demo.errorHandling(true))
                .expectError(RuntimeException.class)
                .verify();
    }
    
    /**
     * 测试并发操作<br>
     * 验证并行执行结果
     */
    @Test
    void testConcurrentOperations() {
        List<String> results = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        
        demo.concurrentOperations()
                .doOnComplete(latch::countDown)
                .subscribe(results::add);
        
        try {
            assertTrue(latch.await(1, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertEquals(3, results.size());
        assertEquals(3, demo.getOperationCount());
    }
    
    /**
     * 测试条件操作<br>
     * 验证不同条件分支
     */
    @Test
    void testConditionalOperation() {
        StepVerifier.create(demo.conditionalOperation(-1))
                .expectNext("Negative")
                .verifyComplete();
        
        demo.resetCounter();
        
        StepVerifier.create(demo.conditionalOperation(0))
                .expectNext("Zero")
                .verifyComplete();
        
        demo.resetCounter();
        
        StepVerifier.create(demo.conditionalOperation(1))
                .expectNext("Positive")
                .verifyComplete();
    }
    
    /**
     * 测试背压<br>
     * 验证背压机制
     */
    @Test
    void testBackpressure() {
        StepVerifier.create(demo.backpressureDemo().take(5))
                .expectNext(2, 4, 6, 8, 10)
                .verifyComplete();
        
        assertEquals(5, demo.getOperationCount());
    }
    
    /**
     * 测试调试钩子<br>
     * 验证调试信息收集
     */
    @Test
    void testDebugHooks() {
        StepVerifier.create(demo.debugHooks("test"))
                .expectNext("Final: TEST")
                .verifyComplete();
        
        assertEquals(1, demo.getOperationCount());
    }
    
    /**
     * 测试虚拟时间<br>
     * 使用VirtualTimeScheduler加速测试
     */
    @Test
    void testVirtualTime() {
        VirtualTimeScheduler.getOrSet();
        
        StepVerifier.withVirtualTime(() -> demo.virtualTimeOperations())
                .expectSubscription()
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNext("Tick 0")
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNext("Tick 1")
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNext("Tick 2")
                .verifyComplete();
        
        assertEquals(3, demo.getOperationCount());
    }
} 