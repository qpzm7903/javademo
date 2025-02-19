package com.qpzm7903.base20.concept10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 错误信号传播机制测试
 */
public class ErrorPropagationDemoTest {
    
    private ErrorPropagationDemo demo;
    
    @BeforeEach
    void setUp() {
        demo = new ErrorPropagationDemo();
        demo.resetCounters();
    }
    
    /**
     * 测试基础错误处理<br>
     * 验证错误传播和计数
     */
    @Test
    void testBasicErrorHandling() {
        StepVerifier.create(demo.basicErrorHandling(true))
                .expectError(ServiceException.class)
                .verify();
        
        assertEquals(1, demo.getErrorCount());
        
        StepVerifier.create(demo.basicErrorHandling(false))
                .expectNext("Success")
                .verifyComplete();
    }
    
    /**
     * 测试错误恢复策略<br>
     * 验证错误恢复和降级
     */
    @Test
    void testErrorRecovery() {
        StepVerifier.create(demo.errorRecovery(true))
                .expectNext("Fallback Value")
                .verifyComplete();
        
        assertEquals(1, demo.getErrorCount());
        assertEquals(1, demo.getFallbackCount());
    }
    
    /**
     * 测试错误重试策略<br>
     * 验证重试机制
     */
    @Test
    void testErrorRetry() {
        StepVerifier.create(demo.errorRetry(true))
                .expectError(ServiceException.class)
                .verify();
        
        assertEquals(4, demo.getErrorCount());  // 初始 + 3次重试
        assertEquals(3, demo.getRetryCount());
    }
    
    /**
     * 测试错误转换策略<br>
     * 验证错误类型转换
     */
    @Test
    void testErrorTransformation() {
        StepVerifier.create(demo.errorTransformation(true))
                .expectError(BusinessException.class)
                .verify();
        
        assertEquals(1, demo.getErrorCount());
    }
    
    /**
     * 测试条件错误处理<br>
     * 验证不同类型错误的处理
     */
    @Test
    void testConditionalErrorHandling() {
        StepVerifier.create(demo.conditionalErrorHandling(new ServiceException("test")))
                .expectNext("Service Fallback")
                .verifyComplete();
        
        demo.resetCounters();
        
        StepVerifier.create(demo.conditionalErrorHandling(new BusinessException("test")))
                .expectNext("Business Fallback")
                .verifyComplete();
        
        demo.resetCounters();
        
        StepVerifier.create(demo.conditionalErrorHandling(new RuntimeException("test")))
                .expectError(RuntimeException.class)
                .verify();
    }
    
    /**
     * 测试错误恢复链<br>
     * 验证多级错误恢复
     */
    @Test
    void testErrorRecoveryChain() {
        StepVerifier.create(demo.errorRecoveryChain(true))
                .expectNext("Final Fallback")
                .verifyComplete();
        
        assertEquals(2, demo.getErrorCount());
        assertEquals(1, demo.getFallbackCount());
    }
    
    /**
     * 测试并行错误处理<br>
     * 验证并行流中的错误处理
     */
    @Test
    void testParallelErrorHandling() {
        AtomicInteger successCount = new AtomicInteger(0);
        
        demo.parallelErrorHandling()
                .doOnNext(s -> successCount.incrementAndGet())
                .blockLast(Duration.ofSeconds(1));
        
        assertEquals(2, demo.getErrorCount());  // 偶数位置的错误
        assertEquals(3, successCount.get());    // 奇数位置的成功
    }
} 