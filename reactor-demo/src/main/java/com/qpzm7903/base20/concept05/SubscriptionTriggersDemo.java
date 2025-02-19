package com.qpzm7903.base20.concept05;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * 订阅触发机制(Subscription Triggers)演示
 * 
 * 典型场景1：延迟加载
 * - 命令式：立即加载所有数据
 * - 响应式：订阅时才加载数据
 * 
 * 典型场景2：缓存刷新
 * - 命令式：定时任务刷新
 * - 响应式：订阅触发刷新
 */
public class SubscriptionTriggersDemo {
    private final AtomicInteger databaseCallCount = new AtomicInteger(0);
    private final AtomicInteger cacheRefreshCount = new AtomicInteger(0);
    
    /**
     * 基础订阅示例
     * 特点：subscribe()触发数据流
     */
    public Flux<Integer> basicSubscription() {
        return Flux.range(1, 5)
                .doOnSubscribe(s -> System.out.println("基础订阅触发"))
                .doOnNext(i -> System.out.println("发送数据: " + i));
    }
    
    /**
     * 延迟订阅示例
     * 特点：delaySubscription()延迟触发订阅
     */
    public Flux<Integer> delayedSubscription() {
        return Flux.range(1, 5)
                .delaySubscription(Duration.ofSeconds(2))
                .doOnSubscribe(s -> System.out.println("延迟订阅触发"))
                .doOnNext(i -> System.out.println("发送数据: " + i));
    }
    
    /**
     * 条件订阅示例
     * 特点：当条件满足时才触发订阅
     */
    public Flux<String> conditionalSubscription(boolean condition) {
        return Flux.defer(() -> {
            if (condition) {
                return Flux.just("数据1", "数据2", "数据3");
            } else {
                return Flux.empty();
            }
        }).doOnSubscribe(s -> System.out.println("条件订阅触发"));
    }
    
    /**
     * 延迟加载数据库示例
     * 特点：只有在订阅时才会查询数据库
     */
    public Mono<String> lazyDatabaseQuery(String id) {
        return Mono.defer(() -> {
            databaseCallCount.incrementAndGet();
            return Mono.just("数据库结果: " + id);
        }).doOnSubscribe(s -> System.out.println("数据库查询触发"));
    }
    
    /**
     * 缓存刷新示例
     * 特点：订阅触发缓存刷新
     */
    public Flux<String> cacheRefresh() {
        return Flux.defer(() -> {
            cacheRefreshCount.incrementAndGet();
            return Flux.just("缓存数据1", "缓存数据2")
                    .delayElements(Duration.ofMillis(100));
        }).doOnSubscribe(s -> System.out.println("缓存刷新触发"));
    }
    
    /**
     * 重试订阅示例
     * 特点：失败时自动重新订阅
     */
    public Flux<String> retryingSubscription() {
        return Flux.defer(() -> {
            if (Math.random() < 0.5) {
                return Flux.error(new RuntimeException("随机失败"));
            }
            return Flux.just("成功数据");
        })
        .retry(3)
        .doOnSubscribe(s -> System.out.println("重试订阅触发"));
    }
    
    /**
     * 并行订阅示例
     * 特点：在不同线程上并行处理订阅
     */
    public Flux<String> parallelSubscription() {
        return Flux.range(1, 5)
                .parallel(2)
                .runOn(Schedulers.parallel())
                .map(i -> "并行处理 #" + i)
                .sequential()
                .doOnSubscribe(s -> System.out.println("并行订阅触发"));
    }
    
    // 获取统计信息
    public int getDatabaseCallCount() {
        return databaseCallCount.get();
    }
    
    public int getCacheRefreshCount() {
        return cacheRefreshCount.get();
    }
} 