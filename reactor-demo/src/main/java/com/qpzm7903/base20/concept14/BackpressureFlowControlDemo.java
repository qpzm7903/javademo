package com.qpzm7903.base20.concept14;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

/**
 * 背压策略与流量控制演示<br>
 * <p>
 * 典型场景：<br>
 * 1. 生产者速度快于消费者<br>
 * 2. 资源限制处理<br>
 * 3. 突发流量控制<br>
 */
public class BackpressureFlowControlDemo {
    private final AtomicInteger producedCount = new AtomicInteger(0);
    private final AtomicInteger consumedCount = new AtomicInteger(0);
    private final AtomicInteger droppedCount = new AtomicInteger(0);
    
    /**
     * 基础背压示例<br>
     * 使用基本的request机制
     */
    public Flux<Integer> basicBackpressure() {
        return Flux.range(1, 1000)
                .doOnNext(i -> producedCount.incrementAndGet())
                .doOnRequest(r -> System.out.println("Requested " + r + " items"))
                .doOnNext(i -> consumedCount.incrementAndGet());
    }
    
    /**
     * 缓冲策略示例<br>
     * 使用buffer控制流量
     */
    public Flux<List<Integer>> bufferStrategy() {
        return Flux.range(1, 100)
                .doOnNext(i -> producedCount.incrementAndGet())
                .buffer(10)  // 每10个元素分组
                .doOnNext(list -> {
                    System.out.println("Processing batch of " + list.size());
                    consumedCount.addAndGet(list.size());
                });
    }
    
    /**
     * 丢弃策略示例<br>
     * 使用onBackpressureDrop处理过载
     */
    public Flux<Long> dropStrategy() {
        return Flux.interval(Duration.ofMillis(1))
                .onBackpressureDrop(i -> droppedCount.incrementAndGet())
                .publishOn(Schedulers.boundedElastic(), 1)
                .doOnNext(i -> {
                    producedCount.incrementAndGet();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    consumedCount.incrementAndGet();
                })
                .take(10);
    }
    
    /**
     * 最新值策略示例<br>
     * 使用onBackpressureLatest保留最新值
     */
    public Flux<Long> latestStrategy() {
        return Flux.interval(Duration.ofMillis(1))
                .onBackpressureLatest()
                .doOnNext(i -> {
                    producedCount.incrementAndGet();
                    consumedCount.incrementAndGet();
                })
                .take(100);
    }
    
    /**
     * 错误策略示例<br>
     * 使用onBackpressureError处理过载
     */
    public Flux<Long> errorStrategy() {
        return Flux.interval(Duration.ofMillis(1))
                .onBackpressureError()
                .doOnNext(i -> {
                    producedCount.incrementAndGet();
                    consumedCount.incrementAndGet();
                })
                .take(100);
    }
    
    /**
     * 自定义背压策略示例<br>
     * 实现自定义的背压处理逻辑
     */
    public Flux<Integer> customBackpressure() {
        return Flux.create(sink -> {
            for (int i = 0; i < 1000 && !sink.isCancelled(); i++) {
                if (sink.requestedFromDownstream() > 0) {
                    producedCount.incrementAndGet();
                    sink.next(i);
                    consumedCount.incrementAndGet();
                } else {
                    try {
                        Thread.sleep(10);  // 等待下游请求
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
            sink.complete();
        }, FluxSink.OverflowStrategy.BUFFER);
    }
    
    /**
     * 限速策略示例<br>
     * 使用limitRate控制流速
     */
    public Flux<Integer> rateLimitStrategy() {
        return Flux.range(1, 1000)
                .doOnNext(i -> producedCount.incrementAndGet())
                .limitRate(10)  // 限制请求率
                .doOnNext(i -> consumedCount.incrementAndGet());
    }
    
    /**
     * 采样策略示例<br>
     * 使用sample控制采样率
     */
    public Flux<Long> samplingStrategy() {
        return Flux.interval(Duration.ofMillis(10))
                .doOnNext(i -> producedCount.incrementAndGet())
                .sample(Duration.ofMillis(50))
                .doOnNext(i -> consumedCount.incrementAndGet());
    }
    
    // 获取统计信息
    public int getProducedCount() {
        return producedCount.get();
    }
    
    public int getConsumedCount() {
        return consumedCount.get();
    }
    
    public int getDroppedCount() {
        return droppedCount.get();
    }
    
    // 重置计数器
    public void resetCounters() {
        producedCount.set(0);
        consumedCount.set(0);
        droppedCount.set(0);
    }
} 