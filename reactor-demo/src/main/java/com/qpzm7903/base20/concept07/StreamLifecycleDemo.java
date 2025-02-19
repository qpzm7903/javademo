package com.qpzm7903.base20.concept07;

import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 数据流生命周期（Assembly vs Subscription）演示<br>
 * <p>
 * 生命周期阶段：<br>
 * 1. 装配期（Assembly Time）- 定义操作符链<br>
 * 2. 订阅期（Subscription Time）- 建立订阅关系<br>
 * 3. 运行期（Runtime）- 数据流动和处理<br>
 * 4. 完成期（Completion）- 正常完成或错误终止<br>
 */
public class StreamLifecycleDemo {
    private final AtomicInteger assemblyCount = new AtomicInteger(0);
    private final AtomicInteger subscriptionCount = new AtomicInteger(0);
    private final AtomicInteger runtimeCount = new AtomicInteger(0);
    
    /**
     * 完整生命周期示例<br>
     * 展示数据流从装配到完成的各个阶段
     */
    public Flux<String> demonstrateLifecycle() {
        // 装配期：定义操作符链
        return Flux.just("A", "B", "C")
                .doOnSubscribe(s -> {
                    subscriptionCount.incrementAndGet();
                    System.out.println("订阅开始");
                })
                .doOnNext(value -> {
                    runtimeCount.incrementAndGet();
                    System.out.println("处理数据: " + value);
                })
                .map(this::transform)
                .doOnComplete(() -> System.out.println("处理完成"))
                .doOnCancel(() -> System.out.println("处理取消"))
                .doFinally(signal -> System.out.println("最终清理: " + signal));
    }
    
    /**
     * 装配期行为示例<br>
     * 展示在数据流定义时的行为
     */
    public Flux<Integer> assemblyTimeExample() {
        assemblyCount.incrementAndGet();
        System.out.println("装配操作符链");
        
        return Flux.range(1, 3)
                .map(i -> i * 2)
                .filter(i -> i > 2);
    }
    
    /**
     * 订阅期行为示例<br>
     * 展示订阅建立时的行为
     */
    public Mono<String> subscriptionTimeExample() {
        return Mono.defer(() -> {
            subscriptionCount.incrementAndGet();
            System.out.println("创建新的订阅");
            return Mono.just("订阅时数据");
        });
    }
    
    /**
     * 运行期行为示例<br>
     * 展示数据处理过程中的行为
     */
    public Flux<String> runtimeExample() {
        return Flux.just("X", "Y", "Z")
                .doOnNext(value -> {
                    runtimeCount.incrementAndGet();
                    System.out.println("运行时处理: " + value);
                });
    }
    
    /**
     * 错误处理生命周期示例<br>
     * 展示错误发生时的生命周期行为
     */
    public Flux<String> errorLifecycleExample() {
        return Flux.just("1", "2", "error", "3")
                .map(value -> {
                    if ("error".equals(value)) {
                        throw new RuntimeException("预期的错误");
                    }
                    return value;
                })
                .doOnError(e -> System.out.println("错误处理: " + e.getMessage()))
                .onErrorResume(e -> Flux.just("恢复值"));
    }
    
    private String transform(String value) {
        return "转换后_" + value;
    }
    
    // 获取统计信息
    public int getAssemblyCount() {
        return assemblyCount.get();
    }
    
    public int getSubscriptionCount() {
        return subscriptionCount.get();
    }
    
    public int getRuntimeCount() {
        return runtimeCount.get();
    }
    
    // 重置计数器
    public void resetCounters() {
        assemblyCount.set(0);
        subscriptionCount.set(0);
        runtimeCount.set(0);
    }
} 