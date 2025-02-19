package com.qpzm7903.base20.concept09;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 空流处理策略测试
 */
public class EmptyStreamDemoTest {
    
    private EmptyStreamDemo demo = new EmptyStreamDemo();
    
    /**
     * 测试命令式空值处理<br>
     * 验证Optional的使用
     */
    @Test
    void testImperativeEmptyHandling() {
        Optional<String> result = demo.findUserImperative("empty");
        assertTrue(result.isEmpty());
        
        Optional<String> nonEmpty = demo.findUserImperative("123");
        assertTrue(nonEmpty.isPresent());
        assertEquals("User_123", nonEmpty.get());
    }
    
    /**
     * 测试基础空流处理<br>
     * 验证Mono.empty()的行为
     */
    @Test
    void testBasicEmptyStream() {
        StepVerifier.create(demo.findUser("empty"))
                .verifyComplete();
        
        StepVerifier.create(demo.findUser("123"))
                .expectNext("User_123")
                .verifyComplete();
    }
    
    /**
     * 测试默认值策略<br>
     * 验证defaultIfEmpty的行为
     */
    @Test
    void testDefaultValueStrategy() {
        StepVerifier.create(demo.findUserWithDefault("empty"))
                .expectNext("Default_User")
                .verifyComplete();
        
        StepVerifier.create(demo.findUserWithDefault("123"))
                .expectNext("User_123")
                .verifyComplete();
    }
    
    /**
     * 测试备选流策略<br>
     * 验证switchIfEmpty的行为
     */
    @Test
    void testFallbackStrategy() {
        StepVerifier.create(demo.findUserWithFallback("empty"))
                .expectNext("Backup_User_empty")
                .verifyComplete();
        
        StepVerifier.create(demo.findUserWithFallback("123"))
                .expectNext("User_123")
                .verifyComplete();
    }
    
    /**
     * 测试转换空流策略<br>
     * 验证空流转换为错误的行为
     */
    @Test
    void testTransformEmptyStream() {
        StepVerifier.create(demo.findUserWithTransform("empty"))
                .expectError(UserNotFoundException.class)
                .verify();
        
        StepVerifier.create(demo.findUserWithTransform("123"))
                .expectNext("User_123")
                .verifyComplete();
    }
    
    /**
     * 测试条件空流处理<br>
     * 验证基于条件的空流处理
     */
    @Test
    void testConditionalEmptyHandling() {
        StepVerifier.create(demo.findUserWithCondition("empty", true))
                .expectNext("Default_User")
                .verifyComplete();
        
        StepVerifier.create(demo.findUserWithCondition("empty", false))
                .expectError(UserNotFoundException.class)
                .verify();
    }
    
    /**
     * 测试组合空流处理<br>
     * 验证多个空流的组合处理
     */
    @Test
    void testCombinedEmptyStreams() {
        StepVerifier.create(demo.findUserAndProfile("empty"))
                .expectNext(Tuples.of("Unknown_User", "Default_Profile"))
                .verifyComplete();
        
        StepVerifier.create(demo.findUserAndProfile("123"))
                .expectNext(Tuples.of("User_123", "Profile_123"))
                .verifyComplete();
    }
    
    /**
     * 测试过滤空值<br>
     * 验证空值过滤的行为
     */
    @Test
    void testFilteredEmptyStream() {
        StepVerifier.create(demo.findUserFiltered("filtered"))
                .expectNext("Filtered_Default")
                .verifyComplete();
        
        StepVerifier.create(demo.findUserFiltered("123"))
                .expectNext("User_123")
                .verifyComplete();
    }
} 