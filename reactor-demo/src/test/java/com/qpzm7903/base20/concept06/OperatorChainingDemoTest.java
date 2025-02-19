package com.qpzm7903.base20.concept06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 操作符链惰性特性测试
 */
public class OperatorChainingDemoTest {
    
    private OperatorChainingDemo demo;
    
    @BeforeEach
    void setUp() {
        demo = new OperatorChainingDemo();
        demo.resetCounters();
    }
    
    /**
     * 测试命令式vs响应式转换<br>
     * 验证响应式转换的惰性特性
     */
    @Test
    void testTransformationExecution() {
        // 命令式转换：立即执行
        int imperativeResult = demo.imperativeTransform(5);
        int imperativeTransformCount = demo.getTransformCount();
        
        demo.resetCounters();
        
        // 响应式转换：创建时不执行
        var reactiveMono = demo.reactiveTransform(5);
        assertEquals(0, demo.getTransformCount(), "创建转换链时不应执行转换");
        
        // 响应式转换：订阅时执行
        StepVerifier.create(reactiveMono)
                .expectNext(imperativeResult)
                .verifyComplete();
        
        assertEquals(imperativeTransformCount, demo.getTransformCount(), 
                "响应式和命令式应该执行相同次数的转换");
    }
    
    /**
     * 测试惰性过滤链<br>
     * 验证过滤条件的惰性执行
     */
    @Test
    void testLazyFilterChain() {
        Flux<Integer> numbers = Flux.just(-1, 1, 2, 3, 4);
        
        // 创建过滤链但不订阅
        var filteredFlux = demo.lazyFilterChain(numbers);
        assertEquals(0, demo.getFilterCount(), "创建过滤链时不应执行过滤");
        assertEquals(0, demo.getTransformCount(), "创建过滤链时不应执行转换");
        
        // 订阅并验证结果
        StepVerifier.create(filteredFlux)
                .expectNext(4, 8)  // 只有2和4通过过滤并被转换
                .verifyComplete();
        
        // 验证过滤和转换的执行次数
        assertEquals(9, demo.getFilterCount(), "应该执行8次过滤检查");
        assertEquals(2, demo.getTransformCount(), "应该执行2次转换");
    }
    
    /**
     * 测试优化的操作符链<br>
     * 验证合并操作后的执行效率
     */
    @Test
    void testOptimizedChain() {
        Flux<Integer> numbers = Flux.just(-1, 1, 2, 3, 4);
        
        StepVerifier.create(demo.optimizedChain(numbers))
                .expectNext(4, 8)  // 结果应该与未优化的链相同
                .verifyComplete();
    }
} 