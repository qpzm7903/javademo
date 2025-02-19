package com.qpzm7903.base20.concept16;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * 热发布者与冷发布者演示<br>
 * <p>
 * 典型场景：<br>
 * 1. 实时数据流（热）<br>
 * 2. 历史数据查询（冷）<br>
 * 3. 事件广播（热）<br>
 */
public class HotColdPublisherDemo {
    private final AtomicInteger subscriptionCount = new AtomicInteger(0);
    private final AtomicInteger dataGenerationCount = new AtomicInteger(0);
    private final Logger logger = LoggerFactory.getLogger(HotColdPublisherDemo.class);
    /**
     * 冷发布者示例<br>
     * 每个订阅者获取完整的数据序列
     */
    public Flux<Integer> coldPublisher() {
        return Flux.range(1, 3)
                .doOnSubscribe(s -> subscriptionCount.incrementAndGet())
                .doOnNext(i -> {
                    dataGenerationCount.incrementAndGet();
                    simulateWork(100);  // 模拟数据生成耗时
                });
    }
    
    /**
     * 热发布者示例（使用connect）<br>
     * 使用ConnectableFlux实现热发布
     */
    public ConnectableFlux<Long> hotPublisherWithConnect() {
        return Flux.interval(Duration.ofMillis(200))
                .doOnNext(i -> dataGenerationCount.incrementAndGet())
                .doOnSubscribe(s -> {
                    logger.info("hotPublisherWithConnect: {}", s);
                    subscriptionCount.incrementAndGet();
                })
                .publish();
    }
    
    /**
     * 热发布者示例（使用Sink）<br>
     * 使用Sinks实现多播
     */
    public Sinks.Many<String> hotPublisherWithSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }
    
    /**
     * 共享热发布者示例<br>
     * 使用share()实现自动连接
     */
    public Flux<Integer> sharedHotPublisher() {
        return Flux.range(1, 5)
                .delayElements(Duration.ofMillis(100))
                .doOnSubscribe(s -> subscriptionCount.incrementAndGet())
                .doOnNext(i -> dataGenerationCount.incrementAndGet())
                .share();
    }
    
    /**
     * 缓存热发布者示例<br>
     * 使用cache()缓存历史数据
     */
    public Flux<String> cachedHotPublisher() {
        return Flux.just("A", "B", "C")
                .doOnSubscribe(s -> subscriptionCount.incrementAndGet())
                .doOnNext(i -> dataGenerationCount.incrementAndGet())
                .cache();
    }
    
    /**
     * 混合发布者示例<br>
     * 组合冷热发布者
     */
    public Flux<String> hybridPublisher() {
        // 冷发布者部分
        Flux<String> cold = Flux.just("Cold1", "Cold2")
                .doOnNext(i -> dataGenerationCount.incrementAndGet());
        
        // 热发布者部分
        Sinks.Many<String> hotSink = Sinks.many().multicast().onBackpressureBuffer();
        Flux<String> hot = hotSink.asFlux()
                .doOnSubscribe(s -> subscriptionCount.incrementAndGet());
        
        // 组合冷热发布者
        return Flux.concat(cold, hot);
    }
    
    /**
     * 重放热发布者示例<br>
     * 使用replay()实现历史数据重放<br>
     * <p>
     * 该方法创建一个热发布者，它会重放最近的两个元素给新订阅者。<br>
     * 适用于需要让新订阅者获取最近历史数据的场景，例如：<br>
     * 1. 实时数据流中，新的观察者需要获取最近的状态更新。<br>
     * 2. 事件流中，新的监听器需要获取最近的事件。<br>
     * </p>
     */
    public Flux<Integer> replayingHotPublisher() {
        return Flux.range(1, 3)
                .doOnSubscribe(s -> subscriptionCount.incrementAndGet())
                .doOnNext(i -> dataGenerationCount.incrementAndGet())
                .replay(2)  // Explicitly set buffer size to 3
                .autoConnect(1); // Connect when first subscriber arrives
    }
    
    /**
     * 条件热发布者示例<br>
     * 根据条件决定是否共享
     */
    public Flux<String> conditionalHotPublisher(boolean share) {
        Flux<String> source = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100))
                .doOnSubscribe(s -> subscriptionCount.incrementAndGet())
                .doOnNext(i -> dataGenerationCount.incrementAndGet());
        
        return share ? source.share() : source;
    }
    
    // 模拟工作负载
    private void simulateWork(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
    
    // 获取统计信息
    public int getSubscriptionCount() {
        return subscriptionCount.get();
    }
    
    public int getDataGenerationCount() {
        return dataGenerationCount.get();
    }
    
    // 重置计数器
    public void resetCounters() {
        subscriptionCount.set(0);
        dataGenerationCount.set(0);
    }
} 