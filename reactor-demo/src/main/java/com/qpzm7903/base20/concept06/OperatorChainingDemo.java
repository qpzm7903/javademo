package com.qpzm7903.base20.concept06;

import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 操作符链(Operator Chaining)的惰性特性演示<br>
 * <p>
 * 典型场景1：数据转换管道<br>
 * - 命令式：每步转换立即执行<br>
 * - 响应式：转换步骤延迟到订阅时执行<br>
 * <p>
 * 典型场景2：条件过滤链<br>
 * - 命令式：所有条件立即检查<br>
 * - 响应式：条件检查延迟到订阅时执行<br>
 */
public class OperatorChainingDemo {
    private final AtomicInteger transformCount = new AtomicInteger(0);
    private final AtomicInteger filterCount = new AtomicInteger(0);
    
    /**
     * 命令式数据转换<br>
     * 特点：立即执行所有转换步骤
     */
    public int imperativeTransform(int input) {
        int step1 = multiply(input, 2);
        int step2 = add(step1, 3);
        int step3 = multiply(step2, 4);
        return step3;
    }
    
    /**
     * 响应式数据转换<br>
     * 特点：转换步骤在订阅时才执行
     */
    public Mono<Integer> reactiveTransform(int input) {
        return Mono.just(input)
                .map(n -> multiply(n, 2))
                .map(n -> add(n, 3))
                .map(n -> multiply(n, 4))
                .doOnSubscribe(s -> System.out.println("开始执行转换链"));
    }
    
    /**
     * 惰性过滤链<br>
     * 特点：<br>
     * 1. 过滤条件在订阅时才检查<br>
     * 2. 一旦不满足条件，后续步骤不执行
     */
    public Flux<Integer> lazyFilterChain(Flux<Integer> numbers) {
        return numbers
                .doOnNext(n -> System.out.println("原始数据: " + n))
                .filter(n -> {
                    boolean result = n > 0;
                    filterCount.incrementAndGet();
                    System.out.println("检查是否大于0: " + n);
                    return result;
                })
                .filter(n -> {
                    boolean result = n % 2 == 0;
                    filterCount.incrementAndGet();
                    System.out.println("检查是否偶数: " + n);
                    return result;
                })
                .map(n -> {
                    transformCount.incrementAndGet();
                    System.out.println("转换数据: " + n);
                    return n * 2;
                });
    }
    
    /**
     * 优化的操作符链<br>
     * 特点：合并多个操作，减少中间步骤
     */
    public Flux<Integer> optimizedChain(Flux<Integer> numbers) {
        return numbers
                .filter(n -> n > 0 && n % 2 == 0)  // 合并过滤条件
                .map(n -> n * 2);                  // 单次转换
    }
    
    // 辅助方法
    private int multiply(int n, int factor) {
        transformCount.incrementAndGet();
        return n * factor;
    }
    
    private int add(int n, int value) {
        transformCount.incrementAndGet();
        return n + value;
    }
    
    // 获取统计信息
    public int getTransformCount() {
        return transformCount.get();
    }
    
    public int getFilterCount() {
        return filterCount.get();
    }
    
    // 重置计数器
    public void resetCounters() {
        transformCount.set(0);
        filterCount.set(0);
    }
} 