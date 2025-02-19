package com.qpzm7903.base20.concept11;

import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * 调度器与线程模型演示<br>
 * <p>
 * 典型场景：<br>
 * 1. IO密集型操作 - 使用boundedElastic<br>
 * 2. CPU密集型操作 - 使用parallel<br>
 * 3. 单线程操作 - 使用single<br>
 * 4. 即时操作 - 使用immediate<br>
 */
public class SchedulerThreadModelDemo {
    private final AtomicInteger taskCount = new AtomicInteger(0);
    
    /**
     * 弹性调度器示例<br>
     * 适用于IO密集型操作，如文件读写、网络请求
     */
    public Flux<String> elasticSchedulerDemo() {
        return Flux.range(1, 5)
                .subscribeOn(Schedulers.boundedElastic())
                .map(i -> {
                    String thread = Thread.currentThread().getName();
                    simulateIoOperation();
                    taskCount.incrementAndGet();
                    return "IO Task " + i + " on thread: " + thread;
                });
    }
    
    /**
     * 并行调度器示例<br>
     * 适用于CPU密集型操作，如数据计算、转换
     */
    public Flux<String> parallelSchedulerDemo() {
        return Flux.range(1, 5)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(i -> {
                    String thread = Thread.currentThread().getName();
                    simulateCpuOperation();
                    taskCount.incrementAndGet();
                    return "CPU Task " + i + " on thread: " + thread;
                })
                .sequential();
    }
    
    /**
     * 单线程调度器示例<br>
     * 适用于需要顺序执行的操作
     */
    public Flux<String> singleSchedulerDemo() {
        return Flux.range(1, 5)
                .publishOn(Schedulers.single())
                .map(i -> {
                    String thread = Thread.currentThread().getName();
                    taskCount.incrementAndGet();
                    return "Single Task " + i + " on thread: " + thread;
                });
    }
    
    /**
     * 即时调度器示例<br>
     * 在当前线程立即执行
     */
    public Flux<String> immediateSchedulerDemo() {
        return Flux.range(1, 5)
                .publishOn(Schedulers.immediate())
                .map(i -> {
                    String thread = Thread.currentThread().getName();
                    taskCount.incrementAndGet();
                    return "Immediate Task " + i + " on thread: " + thread;
                });
    }
    
    /**
     * 混合调度器示例<br>
     * 在不同阶段使用不同的调度器
     */
    public Flux<String> mixedSchedulersDemo() {
        return Flux.range(1, 5)
                .publishOn(Schedulers.parallel())  // CPU密集型操作
                .map(i -> {
                    simulateCpuOperation();
                    return "Processed " + i;
                })
                .publishOn(Schedulers.boundedElastic())  // IO操作
                .map(s -> {
                    simulateIoOperation();
                    taskCount.incrementAndGet();
                    return s + " with IO on " + Thread.currentThread().getName();
                });
    }
    
    /**
     * 自定义调度器示例<br>
     * 使用自定义的调度器执行任务
     */
    public Mono<String> customSchedulerDemo(Scheduler customScheduler) {
        return Mono.just("Custom Task")
                .publishOn(customScheduler)
                .map(s -> {
                    taskCount.incrementAndGet();
                    return s + " on " + Thread.currentThread().getName();
                });
    }
    
    /**
     * 调度器切换示例<br>
     * 展示如何在操作符之间切换调度器
     */
    public Flux<String> schedulerSwitchingDemo() {
        return Flux.range(1, 3)
                .publishOn(Schedulers.parallel())
                .map(i -> "Parallel " + i + " on " + Thread.currentThread().getName())
                .publishOn(Schedulers.single())
                .map(s -> {
                    taskCount.incrementAndGet();
                    return s + " -> Single on " + Thread.currentThread().getName();
                });
    }
    
    // 模拟操作
    private void simulateIoOperation() {
        try {
            Thread.sleep(100);  // 模拟IO延迟
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
    
    private void simulateCpuOperation() {
        // 模拟CPU计算
        for (int i = 0; i < 100000; i++) {
            Math.sqrt(i);
        }
    }
    
    // 获取统计信息
    public int getTaskCount() {
        return taskCount.get();
    }
    
    // 重置计数器
    public void resetCounter() {
        taskCount.set(0);
    }
} 