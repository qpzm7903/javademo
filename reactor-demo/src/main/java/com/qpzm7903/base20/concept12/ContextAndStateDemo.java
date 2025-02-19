package com.qpzm7903.base20.concept12;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Context与状态传递演示<br>
 * <p>
 * 典型场景：<br>
 * 1. 用户认证信息传递<br>
 * 2. 追踪ID传递<br>
 * 3. 事务上下文传递<br>
 */
public class ContextAndStateDemo {
    private final AtomicInteger contextReadCount = new AtomicInteger(0);
    private final AtomicInteger contextWriteCount = new AtomicInteger(0);
    
    /**
     * 基础Context使用示例<br>
     * 展示Context的读写操作
     */
    public Mono<String> basicContextExample() {
        return Mono.just("data")
                .transformDeferredContextual((mono, context) -> {
                    contextReadCount.incrementAndGet();
                    String user = context.get("user");
                    return mono.map(data -> user + " processed " + data);
                })
                .contextWrite(context -> {
                    contextWriteCount.incrementAndGet();
                    return context.put("user", "John");
                });
    }
    
    /**
     * 多层Context示例<br>
     * 展示Context的层级关系
     */
    public Mono<Map<String, String>> multiLayerContext() {
        return Mono.just("data")
                .flatMap(data -> Mono.deferContextual(ctx -> {
                    Map<String, String> result = new HashMap<>();
                    contextReadCount.incrementAndGet();
                    result.put("user", ctx.get("user"));
                    result.put("role", ctx.get("role"));
                    return Mono.just(result);
                }))
                .contextWrite(Context.of("role", "admin"))
                .contextWrite(Context.of("user", "John"));
    }
    
    /**
     * Context传递状态示例<br>
     * 使用Context传递请求状态
     */
    public Flux<String> contextWithState() {
        return Flux.range(1, 3)
                .flatMap(i -> Mono.deferContextual(ctx -> {
                    contextReadCount.incrementAndGet();
                    String txId = ctx.get("transactionId");
                    return Mono.just("Operation " + i + " in transaction " + txId);
                }))
                .contextWrite(context -> {
                    contextWriteCount.incrementAndGet();
                    return context.put("transactionId", "TX-" + System.currentTimeMillis());
                });
    }
    
    /**
     * 动态Context更新示例<br>
     * 根据操作动态更新Context
     */
    public Flux<String> dynamicContextUpdate() {
        return Flux.range(1, 3)
                .flatMap(i -> Mono.deferContextual(ctx -> {
                    contextReadCount.incrementAndGet();
                    int count = ctx.getOrDefault("count", 0);
                    return Mono.just("Step " + i + " (Count: " + count + ")");
                }))
                .contextWrite(context -> {
                    contextWriteCount.incrementAndGet();
                    int currentCount = context.getOrDefault("count", 0);
                    return context.put("count", currentCount + 1);
                });
    }
    
    /**
     * Context合并示例<br>
     * 合并多个Context的内容
     */
    public Mono<String> mergedContext() {
        return Mono.deferContextual(ctx -> {
            contextReadCount.incrementAndGet();
            String user = ctx.get("user");
            String role = ctx.get("role");
            String region = ctx.get("region");
            return Mono.just(String.format("User: %s, Role: %s, Region: %s", user, role, region));
        })
        .contextWrite(Context.of(
            "region", "EU",
            "role", "admin",
            "user", "John"
        ));
    }
    
    /**
     * 条件Context示例<br>
     * 根据条件决定是否添加Context
     */
    public Mono<String> conditionalContext(boolean addExtra) {
        return Mono.deferContextual(ctx -> {
            contextReadCount.incrementAndGet();
            StringBuilder result = new StringBuilder();
            result.append("Base: ").append(ctx.get("base").toString());
            if (ctx.hasKey("extra")) {
                result.append(", Extra: ").append(ctx.get("extra").toString());
            }
            return Mono.just(result.toString());
        })
        .contextWrite(context -> {
            contextWriteCount.incrementAndGet();
            if (addExtra) {
                return context.put("extra", "additional")
                        .put("base", "basic");
            }
            return context.put("base", "basic");
        });
    }
    
    // 获取统计信息
    public int getContextReadCount() {
        return contextReadCount.get();
    }
    
    public int getContextWriteCount() {
        return contextWriteCount.get();
    }
    
    // 重置计数器
    public void resetCounters() {
        contextReadCount.set(0);
        contextWriteCount.set(0);
    }
}