package com.qpzm7903.base20.concept13;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * 测试与调试技巧演示<br>
 * <p>
 * 典型场景：<br>
 * 1. 流程验证<br>
 * 2. 时间控制<br>
 * 3. 错误处理<br>
 * 4. 并发测试<br>
 */
public class TestingDebugDemo {
    private final AtomicInteger operationCount = new AtomicInteger(0);
    
    /**
     * 基础流操作示例<br>
     * 用于演示基本的测试方法
     */
    public Flux<String> basicOperations(String input) {
        return Flux.just(input)
                .map(str -> {
                    operationCount.incrementAndGet();
                    return str.toUpperCase();
                })
                .map(str -> {
                    operationCount.incrementAndGet();
                    return "Processed: " + str;
                });
    }
    
    /**
     * 延时操作示例<br>
     * 用于演示时间相关的测试
     */
    public Flux<String> delayedOperations() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> {
                    operationCount.incrementAndGet();
                    return "Event " + i;
                })
                .take(3);
    }
    
    /**
     * 错误处理示例<br>
     * 用于演示错误处理的测试
     */
    public Mono<String> errorHandling(boolean shouldFail) {
        return Mono.defer(() -> {
            if (shouldFail) {
                return Mono.error(new RuntimeException("Planned failure"));
            }
            operationCount.incrementAndGet();
            return Mono.just("Success");
        });
    }
    
    /**
     * 并发操作示例<br>
     * 用于演示并发测试
     */
    public Flux<String> concurrentOperations() {
        return Flux.range(1, 3)
                .parallel(2)
                .runOn(Schedulers.parallel())
                .map(i -> {
                    operationCount.incrementAndGet();
                    return "Parallel " + i;
                })
                .sequential();
    }
    
    /**
     * 条件操作示例<br>
     * 用于演示条件逻辑的测试
     */
    public Mono<String> conditionalOperation(int value) {
        return Mono.just(value)
                .map(v -> {
                    operationCount.incrementAndGet();
                    if (v < 0) {
                        return "Negative";
                    } else if (v == 0) {
                        return "Zero";
                    } else {
                        return "Positive";
                    }
                });
    }
    
    /**
     * 背压测试示例<br>
     * 用于演示背压机制的测试
     */
    public Flux<Integer> backpressureDemo() {
        return Flux.range(1, 100)
                .map(i -> {
                    operationCount.incrementAndGet();
                    return i * 2;
                });
    }
    
    /**
     * 调试钩子示例<br>
     * 用于演示调试信息的收集
     */
    public Flux<String> debugHooks(String input) {
        return Flux.just(input)
                .doOnNext(s -> System.out.println("Processing: " + s))
                .map(String::toUpperCase)
                .doOnNext(s -> System.out.println("Transformed: " + s))
                .map(s -> {
                    operationCount.incrementAndGet();
                    return "Final: " + s;
                });
    }
    
    /**
     * 虚拟时间示例<br>
     * 用于演示虚拟时间测试
     */
    public Flux<String> virtualTimeOperations() {
        return Flux.interval(Duration.ofMinutes(1))
                .map(i -> {
                    operationCount.incrementAndGet();
                    return "Tick " + i;
                })
                .take(3);
    }
    
    // 获取统计信息
    public int getOperationCount() {
        return operationCount.get();
    }
    
    // 重置计数器
    public void resetCounter() {
        operationCount.set(0);
    }
} 