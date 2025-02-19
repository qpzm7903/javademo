package com.qpzm7903.base20.concept08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * 同步与异步执行边界测试
 */
public class SyncAsyncBoundaryDemoTest {
    
    private SyncAsyncBoundaryDemo demo;
    
    @BeforeEach
    void setUp() {
        demo = new SyncAsyncBoundaryDemo();
        demo.resetCounters();
    }
    
    /**
     * 测试同步操作<br>
     * 验证在当前线程直接执行
     */
    @Test
    void testSynchronousOperation() {
        String result = demo.synchronousOperation("test");
        assertEquals("Sync_test", result);
        assertEquals(1, demo.getSyncOpsCount());
    }
    
    /**
     * 测试异步操作<br>
     * 验证在不同线程异步执行
     */
    @Test
    void testAsynchronousOperation() throws Exception {
        CompletableFuture<String> future = demo.asynchronousOperation("test");
        String result = future.get(1, TimeUnit.SECONDS);
        assertEquals("Async_test", result);
        assertEquals(1, demo.getAsyncOpsCount());
    }
    
    /**
     * 测试同步转异步边界<br>
     * 验证操作在异步线程执行
     */
    @Test
    void testSyncToAsync() {
        Flux<String> input = Flux.just("A", "B", "C");
        
        StepVerifier.create(demo.syncToAsync(input))
                .expectNext("AsyncBoundary_Sync_A")
                .expectNext("AsyncBoundary_Sync_B")
                .expectNext("AsyncBoundary_Sync_C")
                .verifyComplete();
        
        assertEquals(3, demo.getSyncOpsCount());
        assertEquals(3, demo.getAsyncOpsCount());
    }
    
    /**
     * 测试异步转同步边界<br>
     * 验证异步结果在同步环境中处理
     */
    @Test
    void testAsyncToSync() {
        StepVerifier.create(demo.asyncToSync("test"))
                .expectNext("SyncBoundary_Async_test")
                .verifyComplete();
        
        assertEquals(1, demo.getSyncOpsCount());
        assertEquals(1, demo.getAsyncOpsCount());
    }
    
    /**
     * 测试混合边界处理<br>
     * 验证同步异步操作的混合执行
     */
    @Test
    void testMixedBoundaries() {
        Flux<String> input = Flux.just("X", "Y", "Z");
        List<String> results = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        
        demo.mixedBoundaries(input)
                .doOnComplete(latch::countDown)
                .subscribe(results::add);
        
        try {
            assertTrue(latch.await(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertEquals(3, results.size());
        assertTrue(results.stream().allMatch(s -> s.startsWith("Final_Async2_Async1_")));
        assertEquals(3, demo.getSyncOpsCount());
        assertEquals(6, demo.getAsyncOpsCount());
    }
    
    /**
     * 测试并行处理边界<br>
     * 验证并行执行的效果
     */
    @Test
    void testParallelBoundaries() {
        Flux<String> input = Flux.just("1", "2", "3", "4");
        
        StepVerifier.create(demo.parallelBoundaries(input))
                .expectNextCount(4)
                .verifyComplete();
        
        assertEquals(4, demo.getAsyncOpsCount());
    }
}