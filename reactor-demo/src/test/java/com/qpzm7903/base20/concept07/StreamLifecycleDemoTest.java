package com.qpzm7903.base20.concept07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 数据流生命周期测试
 */
public class StreamLifecycleDemoTest {
    
    private StreamLifecycleDemo demo;
    
    @BeforeEach
    void setUp() {
        demo = new StreamLifecycleDemo();
        demo.resetCounters();
    }
    
    /**
     * 测试完整生命周期<br>
     * 验证从装配到完成的各个阶段
     */
    @Test
    void testCompleteLifecycle() {
        Flux<String> flux = demo.demonstrateLifecycle();
        
        // 验证装配期不会触发处理
        assertEquals(0, demo.getSubscriptionCount());
        assertEquals(0, demo.getRuntimeCount());
        
        // 验证订阅和运行期的行为
        StepVerifier.create(flux)
                .expectNext("转换后_A", "转换后_B", "转换后_C")
                .verifyComplete();
        
        assertEquals(1, demo.getSubscriptionCount(), "应该只有一次订阅");
        assertEquals(3, demo.getRuntimeCount(), "应该处理3个元素");
    }
    
    /**
     * 测试装配期行为<br>
     * 验证操作符链的装配过程
     */
    @Test
    void testAssemblyTime() {
        assertEquals(0, demo.getAssemblyCount());
        
        Flux<Integer> flux = demo.assemblyTimeExample();
        assertEquals(1, demo.getAssemblyCount(), "装配应该增加计数");
        
        // 验证多次获取同一个流不会重复装配
        flux.subscribe();
        flux.subscribe();
        assertEquals(1, demo.getAssemblyCount(), "重复订阅不应增加装配计数");
    }
    
    /**
     * 测试订阅期行为<br>
     * 验证订阅建立过程
     */
    @Test
    void testSubscriptionTime() {
        var mono = demo.subscriptionTimeExample();
        assertEquals(0, demo.getSubscriptionCount(), "订阅前计数应为0");
        
        // 第一次订阅
        StepVerifier.create(mono)
                .expectNext("订阅时数据")
                .verifyComplete();
        assertEquals(1, demo.getSubscriptionCount(), "第一次订阅应增加计数");
        
        // 第二次订阅
        StepVerifier.create(mono)
                .expectNext("订阅时数据")
                .verifyComplete();
        assertEquals(2, demo.getSubscriptionCount(), "第二次订阅应再次增加计数");
    }
    
    /**
     * 测试运行期行为<br>
     * 验证数据处理过程
     */
    @Test
    void testRuntime() {
        var flux = demo.runtimeExample();
        assertEquals(0, demo.getRuntimeCount(), "运行前计数应为0");
        
        StepVerifier.create(flux)
                .expectNext("X", "Y", "Z")
                .verifyComplete();
        assertEquals(3, demo.getRuntimeCount(), "应该处理3个元素");
    }
    
    /**
     * 测试错误处理生命周期<br>
     * 验证错误发生时的行为
     */
    @Test
    void testErrorLifecycle() {
        StepVerifier.create(demo.errorLifecycleExample())
                .expectNext("1", "2")
                .expectNext("恢复值")
                .verifyComplete();
    }
} 