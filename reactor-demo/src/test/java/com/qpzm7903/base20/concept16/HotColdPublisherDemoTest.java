package com.qpzm7903.base20.concept16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

/**
 * 热发布者与冷发布者测试
 */
public class HotColdPublisherDemoTest {
    
    private HotColdPublisherDemo demo;
    
    @BeforeEach
    void setUp() {
        demo = new HotColdPublisherDemo();
        demo.resetCounters();
    }
    
    /**
     * 测试冷发布者<br>
     * 验证每个订阅者获取完整序列
     */
    @Test
    void testColdPublisher() {
        Flux<Integer> cold = demo.coldPublisher();
        
        // 第一个订阅者
        StepVerifier.create(cold)
                .expectNext(1, 2, 3)
                .verifyComplete();
        
        // 第二个订阅者
        StepVerifier.create(cold)
                .expectNext(1, 2, 3)
                .verifyComplete();
        
        assertEquals(2, demo.getSubscriptionCount());
        assertEquals(6, demo.getDataGenerationCount());  // 每个订阅者都生成了3个数据
    }
    
    /**
     * 测试热发布者（Connect）<br>
     * 验证多播行为
     */
    @Test
    void testHotPublisherWithConnect() {
        ConnectableFlux<Long> hot = demo.hotPublisherWithConnect();
        List<Long> subscriber1Data = new ArrayList<>();
        List<Long> subscriber2Data = new ArrayList<>();
        
        // Subscribe both subscribers before connecting
        hot.doOnSubscribe(s -> System.out.println("Subscriber 1 subscribed"))
           .subscribe(subscriber1Data::add);
        hot.doOnSubscribe(s -> System.out.println("Subscriber 2 subscribed"))
           .subscribe(subscriber2Data::add);
        
        // Connect and wait for data
        hot.connect();
        
        try {
            Thread.sleep(1000);  // Wait for data emission
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertTrue(subscriber1Data.size() > 0, "Subscriber 1 should receive data");
        assertTrue(subscriber2Data.size() > 0, "Subscriber 2 should receive data");
        assertEquals(1, demo.getSubscriptionCount(), "Should count both subscriptions");
    }
    
    /**
     * 测试热发布者（Sink）<br>
     * 验证Sink的多播特性
     */
    @Test
    void testHotPublisherWithSink() {
        Sinks.Many<String> sink = demo.hotPublisherWithSink();
        List<String> subscriber1Data = new CopyOnWriteArrayList<>();
        List<String> subscriber2Data = new CopyOnWriteArrayList<>();
        
        sink.asFlux().subscribe(subscriber1Data::add);
        sink.asFlux().subscribe(subscriber2Data::add);
        
        sink.tryEmitNext("A");
        sink.tryEmitNext("B");
        sink.tryEmitNext("C");
        
        assertEquals(subscriber1Data, subscriber2Data);
        assertEquals(3, subscriber1Data.size());
    }
    
    /**
     * 测试共享热发布者<br>
     * 验证share()的行为
     */
    @Test
    void testSharedHotPublisher() {
        Flux<Integer> shared = demo.sharedHotPublisher();
        CountDownLatch latch = new CountDownLatch(2);
        List<Integer> subscriber1Data = new CopyOnWriteArrayList<>();
        List<Integer> subscriber2Data = new CopyOnWriteArrayList<>();
        
        shared.subscribe(i -> {
            subscriber1Data.add(i);
            latch.countDown();
        });
        
        // 延迟第二个订阅者
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        shared.subscribe(i -> {
            subscriber2Data.add(i);
            latch.countDown();
        });
        
        try {
            latch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertTrue(subscriber1Data.size() > subscriber2Data.size());
    }
    
    /**
     * 测试缓存热发布者<br>
     * 验证cache()的行为
     */
    @Test
    void testCachedHotPublisher() {
        Flux<String> cached = demo.cachedHotPublisher();
        
        // 第一个订阅者触发数据生成
        StepVerifier.create(cached)
                .expectNext("A", "B", "C")
                .verifyComplete();
        
        // 第二个订阅者使用缓存的数据
        StepVerifier.create(cached)
                .expectNext("A", "B", "C")
                .verifyComplete();
        
        assertEquals(1, demo.getSubscriptionCount());
        assertEquals(3, demo.getDataGenerationCount());  // 数据只生成一次
    }
    
    /**
     * 测试混合发布者<br>
     * 验证冷热组合的行为
     */
    @Test
    void testHybridPublisher() {
        Flux<String> hybrid = demo.hybridPublisher();
        List<String> results = new ArrayList<>();
        
        hybrid.subscribe(results::add);
        
        assertEquals(2, results.size());  // 只接收到冷发布者的数据
        assertEquals("Cold1", results.get(0));
        assertEquals("Cold2", results.get(1));
    }
    
    /**
     * 测试重放热发布者<br>
     * 验证replay()的行为
     */
    @Test
    void testReplayingHotPublisher() {
        Flux<Integer> replaying = demo.replayingHotPublisher();
        List<Integer> subscriber1Data = new ArrayList<>();
        List<Integer> subscriber2Data = new ArrayList<>();
        
        // First subscriber gets all values
        replaying.subscribe(subscriber1Data::add);
        
        // Wait a bit to ensure first subscription completes
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Second subscriber should only get last 2 values from replay buffer
        replaying.subscribe(subscriber2Data::add);
        
        assertEquals(Arrays.asList(1, 2, 3), subscriber1Data);
        assertEquals(Arrays.asList(2, 3), subscriber2Data);  // Only gets last 2 values
        assertEquals(1, demo.getSubscriptionCount());
        assertEquals(3, demo.getDataGenerationCount());
    }
    
    /**
     * 测试条件热发布者<br>
     * 验证条件共享行为
     */
    @Test
    void testConditionalHotPublisher() {
        // 测试共享模式
        Flux<String> shared = demo.conditionalHotPublisher(true);
        List<String> sharedResults1 = new ArrayList<>();
        List<String> sharedResults2 = new ArrayList<>();
        
        shared.subscribe(sharedResults1::add);
        
        try {
            Thread.sleep(150);  // 延迟第二个订阅者
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        shared.subscribe(sharedResults2::add);
        
        assertTrue(sharedResults1.size() >= sharedResults2.size());
        
        // 测试非共享模式
        demo.resetCounters();
        Flux<String> nonShared = demo.conditionalHotPublisher(false);
        
        StepVerifier.create(nonShared)
                .expectNext("A", "B", "C")
                .verifyComplete();
        
        StepVerifier.create(nonShared)
                .expectNext("A", "B", "C")
                .verifyComplete();
    }
} 