package com.qpzm7903.base20.concept11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 调度器与线程模型测试
 */
public class SchedulerThreadModelDemoTest {
    
    private SchedulerThreadModelDemo demo;
    
    @BeforeEach
    void setUp() {
        demo = new SchedulerThreadModelDemo();
        demo.resetCounter();
    }
    
    /**
     * 测试弹性调度器<br>
     * 验证IO密集型操作的执行
     */
    @Test
    void testElasticScheduler() {
        List<String> results = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        
        demo.elasticSchedulerDemo()
                .doOnComplete(latch::countDown)
                .subscribe(results::add);
        
        try {
            assertTrue(latch.await(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertEquals(5, results.size());
        assertEquals(5, demo.getTaskCount());
        assertTrue(results.stream()
                .allMatch(s -> s.contains("boundedElastic")));
    }
    
    /**
     * 测试并行调度器<br>
     * 验证CPU密集型操作的并行执行
     */
    @Test
    void testParallelScheduler() {
        List<String> results = new CopyOnWriteArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        
        demo.parallelSchedulerDemo()
                .doOnComplete(latch::countDown)
                .subscribe(results::add);
        
        try {
            assertTrue(latch.await(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertEquals(5, results.size());
        assertEquals(5, demo.getTaskCount());
        assertTrue(results.stream()
                .allMatch(s -> s.contains("parallel")));
    }
    
    /**
     * 测试单线程调度器<br>
     * 验证顺序执行
     */
    @Test
    void testSingleScheduler() {
        List<String> results = new ArrayList<>();
        
        demo.singleSchedulerDemo()
                .collectList()
                .block(Duration.ofSeconds(1));
        
        assertEquals(5, demo.getTaskCount());
    }
    
    /**
     * 测试即时调度器<br>
     * 验证在当前线程执行
     */
    @Test
    void testImmediateScheduler() {
        String currentThread = Thread.currentThread().getName();
        
        List<String> results = demo.immediateSchedulerDemo()
                .collectList()
                .block(Duration.ofSeconds(1));
        
        assertNotNull(results);
        assertEquals(5, results.size());
        assertTrue(results.stream()
                .allMatch(s -> s.contains(currentThread)));
    }
    
    /**
     * 测试混合调度器<br>
     * 验证不同阶段使用不同调度器
     */
    @Test
    void testMixedSchedulers() {
        List<String> results = new CopyOnWriteArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        
        demo.mixedSchedulersDemo()
                .doOnComplete(latch::countDown)
                .subscribe(results::add);
        
        try {
            assertTrue(latch.await(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertEquals(5, results.size());
        assertEquals(5, demo.getTaskCount());
        assertTrue(results.stream()
                .allMatch(s -> s.contains("boundedElastic")));
    }
    
    /**
     * 测试自定义调度器<br>
     * 验证自定义调度器的使用
     */
    @Test
    void testCustomScheduler() {
        StepVerifier.create(demo.customSchedulerDemo(Schedulers.single()))
                .expectNextMatches(s -> s.contains("single"))
                .verifyComplete();
        
        assertEquals(1, demo.getTaskCount());
    }
    
    /**
     * 测试调度器切换<br>
     * 验证在操作符之间切换调度器
     */
    @Test
    void testSchedulerSwitching() {
        List<String> results = new CopyOnWriteArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        
        demo.schedulerSwitchingDemo()
                .doOnComplete(latch::countDown)
                .subscribe(results::add);
        
        try {
            assertTrue(latch.await(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertEquals(3, results.size());
        assertEquals(3, demo.getTaskCount());
        assertTrue(results.stream()
                .allMatch(s -> s.contains("parallel") && s.contains("single")));
    }
} 