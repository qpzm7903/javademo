package com.qpzm7903.base20.concept18;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 调度器切换与线程边界测试
 */
public class SchedulerSwitchingDemoTest {
    
    private SchedulerSwitchingDemo demo;
    
    @BeforeEach
    void setUp() {
        demo = new SchedulerSwitchingDemo();
        demo.resetCounter();
    }
    
    /**
     * 测试基础调度器切换<br>
     * 验证线程切换行为
     */
    @Test
    void testBasicSchedulerSwitch() {
        List<String> results = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        
        demo.basicSchedulerSwitch()
                .doOnComplete(latch::countDown)
                .subscribe(results::add);
        
        try {
            assertTrue(latch.await(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertEquals(3, results.size());
        assertTrue(results.stream().anyMatch(s -> s.contains("Parallel")));
        assertTrue(results.stream().anyMatch(s -> s.contains("Elastic")));
        assertEquals(9, demo.getOperationCount());
    }
    
    /**
     * 测试订阅线程控制<br>
     * 验证subscribeOn的行为
     */
    @Test
    void testSubscribeOnControl() {
        StepVerifier.create(demo.subscribeOnControl())
                .expectNextMatches(s -> s.contains("boundedElastic"))
                .verifyComplete();
        
        assertEquals(1, demo.getOperationCount());
    }
    
    /**
     * 测试混合调度器<br>
     * 验证多个调度器的组合使用
     */
    @Test
    void testMixedSchedulers() {
        List<String> results = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        
        demo.mixedSchedulers()
                .doOnComplete(latch::countDown)
                .subscribe(results::add);
        
        try {
            assertTrue(latch.await(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertEquals(3, results.size());
        assertTrue(results.stream().anyMatch(s -> s.contains("Single")));
        assertTrue(results.stream().anyMatch(s -> s.contains("Parallel")));
        assertTrue(results.stream().anyMatch(s -> s.contains("Elastic")));
        assertEquals(9, demo.getOperationCount());
    }
    
    /**
     * 测试即时调度器<br>
     * 验证immediate调度器的行为
     */
    @Test
    void testImmediateScheduler() {
        StepVerifier.create(demo.immediateScheduler())
                .expectNextMatches(s -> s.startsWith("Immediate"))
                .verifyComplete();
        
        assertEquals(1, demo.getOperationCount());
    }
    
    /**
     * 测试自定义调度器<br>
     * 验证自定义调度器的使用
     */
    @Test
    void testCustomScheduler() {
        StepVerifier.create(demo.customScheduler(Schedulers.single()))
                .expectNextCount(3)
                .verifyComplete();
        
        assertEquals(3, demo.getOperationCount());
    }
    
    /**
     * 测试条件调度器切换<br>
     * 验证条件调度器选择
     */
    @Test
    void testConditionalScheduling() {
        StepVerifier.create(demo.conditionalScheduling(true))
                .expectNextMatches(s -> s.contains("parallel"))
                .verifyComplete();
        
        demo.resetCounter();
        
        StepVerifier.create(demo.conditionalScheduling(false))
                .expectNextMatches(s -> s.contains("boundedElastic"))
                .verifyComplete();
        
        assertEquals(1, demo.getOperationCount());
    }
    
    /**
     * 测试嵌套调度器<br>
     * 验证嵌套场景下的调度器行为
     */
    @Test
    void testNestedSchedulers() {
        List<String> results = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        
        demo.nestedSchedulers()
                .doOnComplete(latch::countDown)
                .subscribe(results::add);
        
        try {
            assertTrue(latch.await(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(s -> s.contains("boundedElastic")));
        assertEquals(2, demo.getOperationCount());
    }
    
    /**
     * 测试调度器隔离<br>
     * 验证不同类型操作的调度器隔离
     */
    @Test
    void testSchedulerIsolation() {
        List<String> results = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        
        demo.schedulerIsolation()
                .doOnComplete(latch::countDown)
                .subscribe(results::add);
        
        try {
            assertTrue(latch.await(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(s -> s.contains("boundedElastic")));
        assertTrue(results.stream().anyMatch(s -> s.contains("parallel")));
        assertEquals(4, demo.getOperationCount());
    }
} 