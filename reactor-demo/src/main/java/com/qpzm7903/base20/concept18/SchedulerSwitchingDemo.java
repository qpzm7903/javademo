package com.qpzm7903.base20.concept18;

import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * 调度器切换与线程边界演示<br>
 * <p>
 * 典型场景：<br>
 * 1. IO操作与计算分离<br>
 * 2. UI线程与后台处理<br>
 * 3. 资源密集型操作隔离<br>
 */
public class SchedulerSwitchingDemo {
    private final AtomicInteger operationCount = new AtomicInteger(0);
    
    /**
     * 基础调度器切换示例<br>
     * 展示基本的调度器切换
     */
    public Flux<String> basicSchedulerSwitch() {
        return Flux.range(1, 3)
                .map(i -> {
                    operationCount.incrementAndGet();
                    String thread = Thread.currentThread().getName();
                    return "Initial " + i + " on " + thread;
                })
                .publishOn(Schedulers.parallel())
                .map(str -> {
                    operationCount.incrementAndGet();
                    String thread = Thread.currentThread().getName();
                    return str + " -> Parallel on " + thread;
                })
                .publishOn(Schedulers.boundedElastic())
                .map(str -> {
                    operationCount.incrementAndGet();
                    String thread = Thread.currentThread().getName();
                    return str + " -> Elastic on " + thread;
                });
    }
    
    /**
     * 订阅线程控制示例<br>
     * 使用subscribeOn控制订阅发生的线程
     */
    public Mono<String> subscribeOnControl() {
        return Mono.fromCallable(() -> {
            operationCount.incrementAndGet();
            String thread = Thread.currentThread().getName();
            return "Task executed on " + thread;
        })
        .subscribeOn(Schedulers.boundedElastic());
    }
    
    /**
     * 混合调度器示例<br>
     * 在不同阶段使用不同的调度器
     */
    public Flux<String> mixedSchedulers() {
        return Flux.range(1, 3)
                .subscribeOn(Schedulers.single())
                .map(i -> {
                    operationCount.incrementAndGet();
                    String thread = Thread.currentThread().getName();
                    return "Single " + i + " on " + thread;
                })
                .publishOn(Schedulers.parallel())
                .map(str -> {
                    operationCount.incrementAndGet();
                    String thread = Thread.currentThread().getName();
                    return str + " -> Parallel";
                })
                .publishOn(Schedulers.boundedElastic())
                .map(str -> {
                    operationCount.incrementAndGet();
                    String thread = Thread.currentThread().getName();
                    return str + " -> Elastic";
                });
    }
    
    /**
     * 即时调度器示例<br>
     * 使用immediate调度器执行即时操作
     */
    public Mono<String> immediateScheduler() {
        return Mono.just("Immediate")
                .publishOn(Schedulers.immediate())
                .map(str -> {
                    operationCount.incrementAndGet();
                    String thread = Thread.currentThread().getName();
                    return str + " on " + thread;
                });
    }
    
    /**
     * 自定义调度器示例<br>
     * 使用自定义调度器
     */
    public Flux<String> customScheduler(Scheduler scheduler) {
        return Flux.range(1, 3)
                .publishOn(scheduler)
                .map(i -> {
                    operationCount.incrementAndGet();
                    String thread = Thread.currentThread().getName();
                    return "Custom " + i + " on " + thread;
                });
    }
    
    /**
     * 条件调度器切换示例<br>
     * 根据条件选择不同的调度器
     */
    public Mono<String> conditionalScheduling(boolean useParallel) {
        Scheduler scheduler = useParallel ? 
                Schedulers.parallel() : Schedulers.boundedElastic();
        
        return Mono.just("Task")
                .publishOn(scheduler)
                .map(str -> {
                    operationCount.incrementAndGet();
                    String thread = Thread.currentThread().getName();
                    return str + " on " + thread;
                });
    }
    
    /**
     * 嵌套调度器示例<br>
     * 处理嵌套的调度器场景
     */
    public Flux<String> nestedSchedulers() {
        return Flux.range(1, 2)
                .publishOn(Schedulers.parallel())
                .flatMap(i -> Mono.just("Nested " + i)
                        .publishOn(Schedulers.boundedElastic())
                        .map(str -> {
                            operationCount.incrementAndGet();
                            String thread = Thread.currentThread().getName();
                            return str + " on " + thread;
                        }));
    }
    
    /**
     * 调度器隔离示例<br>
     * 为不同类型的操作使用独立的调度器
     */
    public Flux<String> schedulerIsolation() {
        return Flux.range(1, 2)
                .publishOn(Schedulers.boundedElastic())  // IO操作
                .map(i -> {
                    operationCount.incrementAndGet();
                    simulateIoWork();
                    String thread = Thread.currentThread().getName();
                    return "IO " + i + " on " + thread;
                })
                .publishOn(Schedulers.parallel())  // CPU操作
                .map(str -> {
                    operationCount.incrementAndGet();
                    simulateCpuWork();
                    String thread = Thread.currentThread().getName();
                    return str + " -> CPU on " + thread;
                });
    }
    
    // 模拟IO工作
    private void simulateIoWork() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
    
    // 模拟CPU工作
    private void simulateCpuWork() {
        for (int i = 0; i < 100000; i++) {
            Math.sqrt(i);
        }
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