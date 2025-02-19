package com.qpzm7903.base20.concept08;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * 同步与异步执行边界演示<br>
 * <p>
 * 典型场景：<br>
 * 1. 同步操作转异步<br>
 * 2. 异步操作转同步<br>
 * 3. 混合边界处理<br>
 */
public class SyncAsyncBoundaryDemo {
    private final AtomicInteger syncOps = new AtomicInteger(0);
    private final AtomicInteger asyncOps = new AtomicInteger(0);
    
    /**
     * 同步操作示例<br>
     * 在调用线程上直接执行
     */
    public String synchronousOperation(String input) {
        syncOps.incrementAndGet();
        return "Sync_" + input;
    }
    
    /**
     * 异步操作示例<br>
     * 使用CompletableFuture在不同线程上执行
     */
    public CompletableFuture<String> asynchronousOperation(String input) {
        return CompletableFuture.supplyAsync(() -> {
            asyncOps.incrementAndGet();
            simulateDelay(100);
            return "Async_" + input;
        });
    }
    
    /**
     * 同步转异步边界示例<br>
     * 使用publishOn将同步操作转换为异步执行
     */
    public Flux<String> syncToAsync(Flux<String> input) {
        return input
                .map(this::synchronousOperation)  // 同步操作
                .publishOn(Schedulers.boundedElastic())  // 切换到异步边界
                .map(str -> {
                    asyncOps.incrementAndGet();
                    return "AsyncBoundary_" + str;
                });
    }
    
    /**
     * 异步转同步边界示例<br>
     * 使用subscribeOn将异步操作结果转换回同步处理
     */
    public Mono<String> asyncToSync(String input) {
        return Mono.fromFuture(asynchronousOperation(input))
                .subscribeOn(Schedulers.boundedElastic())
                .map(str -> {
                    syncOps.incrementAndGet();
                    return "SyncBoundary_" + str;
                });
    }
    
    /**
     * 混合边界处理示例<br>
     * 同时包含同步和异步操作的处理流
     */
    public Flux<String> mixedBoundaries(Flux<String> input) {
        return input
                .publishOn(Schedulers.parallel())  // 第一个异步边界
                .map(str -> {
                    asyncOps.incrementAndGet();
                    return "Async1_" + str;
                })
                .publishOn(Schedulers.boundedElastic())  // 第二个异步边界
                .map(str -> {
                    asyncOps.incrementAndGet();
                    return "Async2_" + str;
                })
                .subscribeOn(Schedulers.single())  // 订阅发生在单独的线程
                .map(str -> {
                    syncOps.incrementAndGet();
                    return "Final_" + str;
                });
    }
    
    /**
     * 并行处理边界示例<br>
     * 使用parallel()和runOn()创建并行处理边界
     */
    public Flux<String> parallelBoundaries(Flux<String> input) {
        return input
                .parallel(2)  // 分割成2个并行rail
                .runOn(Schedulers.parallel())
                .map(str -> {
                    asyncOps.incrementAndGet();
                    return "Parallel_" + str;
                })
                .sequential();  // 重新组合成顺序流
    }
    
    private void simulateDelay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
    
    // 获取统计信息
    public int getSyncOpsCount() {
        return syncOps.get();
    }
    
    public int getAsyncOpsCount() {
        return asyncOps.get();
    }
    
    // 重置计数器
    public void resetCounters() {
        syncOps.set(0);
        asyncOps.set(0);
    }
} 